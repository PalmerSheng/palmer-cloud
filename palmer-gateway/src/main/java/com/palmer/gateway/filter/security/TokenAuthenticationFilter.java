//package com.palmer.gateway.filter.security;
//
//import com.google.common.cache.CacheLoader;
//import com.google.common.cache.LoadingCache;
//import com.palmer.framework.common.core.KeyValue;
//import com.palmer.framework.common.util.cache.CacheUtils;
//import com.palmer.gateway.util.JsonUtils;
//import com.palmer.gateway.util.SecurityFrameworkUtils;
//import com.palmer.gateway.util.WebFrameworkUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.time.Duration;
//import java.util.Objects;
//import java.util.function.Function;
//
///**
// * @author palmer
// * @date 2023-11-16
// */
//@Slf4j
//@Component
//public class TokenAuthenticationFilter implements GlobalFilter, Ordered {
//    /**
//     * 空的 LoginUser 的结果
//     *
//     * 用于解决如下问题：
//     * 1. {@link #getLoginUser(ServerWebExchange, String)} 返回 Mono.empty() 时，会导致后续的 flatMap 无法进行处理的问题。
//     * 2. {@link #buildUser(String)} 时，如果 Token 已经过期，返回 LOGIN_USER_EMPTY 对象，避免缓存无法刷新
//     */
//    private static final LoginUser LOGIN_USER_EMPTY = new LoginUser();
//
//    private final WebClient webClient;
//
//
//    public TokenAuthenticationFilter(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
//        // Q：为什么不使用 OAuth2TokenApi 进行调用？
//        // A1：Spring Cloud OpenFeign 官方未内置 Reactive 的支持 https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#reactive-support
//        // A2：校验 Token 的 API 需要使用到 header[tenant-id] 传递租户编号，暂时不想编写 RequestInterceptor 实现
//        // 因此，这里采用 WebClient，通过 lbFunction 实现负载均衡
//        this.webClient = WebClient.builder().filter(lbFunction).build();
//    }
//
//    /**
//     * 登录用户的本地缓存
//     *
//     * key1：多租户的编号
//     * key2：访问令牌
//     */
//    private final LoadingCache<KeyValue<Long, String>, LoginUser> loginUserCache = CacheUtils.buildAsyncReloadingCache(Duration.ofMinutes(1),
//            new CacheLoader<KeyValue<Long, String>, LoginUser>() {
//
//                @Override
//                public LoginUser load(KeyValue<Long, String> token) {
//                    String body = checkAccessToken(token.getKey(), token.getValue()).block();
//                    return buildUser(body);
//                }
//
//            });
//
//
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 这一步是对请求头做操作，移除和加入一些属性， 在整个请求链路中，用户认证需要一些基本数据
//        // 比如用户id，用户类型 ，租户  这些可以通过请求的请求头或者token获取到
//
//        // 移除请求头中的用户信息
//        SecurityFrameworkUtils.removeLoginUser(exchange);
//
//        // 如果请求头中没有用户信息 ，则直接继续filter
//        String token = SecurityFrameworkUtils.obtainAuthorization(exchange);
//        if (!StringUtils.hasText(token)) {
//            return chain.filter(exchange);
//        }
//
//        // 解析请求头里面的token（如果有） ，并放入请求头中
//        // 如果没有，则继续请求
//        return getLoginUser(exchange, token).defaultIfEmpty(LOGIN_USER_EMPTY).flatMap(user -> {
//            // 1. 无用户，直接 filter 继续请求
//            if (user == LOGIN_USER_EMPTY) {
//                return chain.filter(exchange);
//            }
//
//            // 2.1 有用户，则设置登录用户
//            SecurityFrameworkUtils.setLoginUser(exchange, user);
//            // 2.2 将 user 并设置到 login-user 的请求头，使用 json 存储值
//            ServerWebExchange newExchange = exchange.mutate()
//                    .request(builder -> SecurityFrameworkUtils.setLoginUserHeader(builder, user)).build();
//            return chain.filter(newExchange);
//        });
//    }
//
//    private Mono<LoginUser> getLoginUser(ServerWebExchange exchange, String token) {
//        // 从缓存中，获取 LoginUser
//        Long tenantId = WebFrameworkUtils.getTenantId(exchange);
//        KeyValue<Long, String> cacheKey = new KeyValue<Long, String>().setKey(tenantId).setValue(token);
//        LoginUser localUser = loginUserCache.getIfPresent(cacheKey);
//        if (localUser != null) {
//            return Mono.just(localUser);
//        }
//
//        // 缓存不存在，则请求远程服务
//        return checkAccessToken(tenantId, token).flatMap((Function<String, Mono<LoginUser>>) body -> {
//            LoginUser remoteUser = buildUser(body);
//            if (remoteUser != null) {
//                // 非空，则进行缓存
//                loginUserCache.put(cacheKey, remoteUser);
//                return Mono.just(remoteUser);
//            }
//            return Mono.empty();
//        });
//
//
//    }
//
//
//    private Mono<String> checkAccessToken(Long tenantId, String token) {
//        return webClient.get()
//                .uri(OAuth2TokenApi.URL_CHECK, uriBuilder -> uriBuilder.queryParam("accessToken", token).build())
//                .headers(httpHeaders -> WebFrameworkUtils.setTenantIdHeader(tenantId, httpHeaders)) // 设置租户的 Header
//                .retrieve().bodyToMono(String.class);
//    }
//
//
//    private LoginUser buildUser(String body) {
//        // 处理结果，结果不正确
//        CommonResult<OAuth2AccessTokenCheckRespDTO> result = JsonUtils.parseObject(body, CHECK_RESULT_TYPE_REFERENCE);
//        if (result == null) {
//            return null;
//        }
//        if (result.isError()) {
//            // 特殊情况：令牌已经过期（code = 401），需要返回 LOGIN_USER_EMPTY，避免 Token 一直因为缓存，被误判为有效
//            if (Objects.equals(result.getCode(), HttpStatus.UNAUTHORIZED.value())) {
//                return LOGIN_USER_EMPTY;
//            }
//            return null;
//        }
//
//        // 创建登录用户
//        OAuth2AccessTokenCheckRespDTO tokenInfo = result.getData();
//        return new LoginUser().setId(tokenInfo.getUserId()).setUserType(tokenInfo.getUserType())
//                .setTenantId(tokenInfo.getTenantId()).setScopes(tokenInfo.getScopes());
//    }
//
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
