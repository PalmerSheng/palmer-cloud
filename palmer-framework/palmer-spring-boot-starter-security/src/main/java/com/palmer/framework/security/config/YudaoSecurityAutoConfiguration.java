package com.palmer.framework.security.config;

import com.palmer.framework.security.core.filter.TokenAuthenticationFilter;
import com.palmer.framework.security.core.handler.AccessDeniedHandlerImpl;
import com.palmer.framework.security.core.handler.AuthenticationEntryPointImpl;
import com.palmer.module.system.api.oauth2.OAuth2TokenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;

/**
 * @author palmer
 * @date 2023-11-15
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class YudaoSecurityAutoConfiguration {

    @Resource
    private SecurityProperties securityProperties;
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(SecurityProperties securityProperties, OAuth2TokenApi oauth2TokenApi) {
        return new TokenAuthenticationFilter(securityProperties,oauth2TokenApi);
    }


    /**
     * 认证失败处理类 Bean
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 权限不够处理器 Bean
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
    }

//    @Bean("ss") // 使用 Spring Security 的缩写，方便使用
//    public SecurityFrameworkService securityFrameworkService(PermissionApi permissionApi) {
//        return new SecurityFrameworkServiceImpl(permissionApi);
//    }


}
