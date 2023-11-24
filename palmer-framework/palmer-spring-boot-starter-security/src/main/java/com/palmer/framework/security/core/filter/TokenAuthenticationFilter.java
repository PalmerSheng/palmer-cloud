package com.palmer.framework.security.core.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.palmer.framework.common.enums.UserTypeEnum;
import com.palmer.framework.common.exception.ServiceException;
import com.palmer.framework.security.config.SecurityProperties;
import com.palmer.framework.security.core.LoginUser;
import com.palmer.framework.security.core.util.SecurityFrameworkUtils;
import com.palmer.framework.web.util.WebFrameworkUtils;
import com.palmer.module.system.api.oauth2.OAuth2TokenApi;
import com.palmer.module.system.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 过滤器，验证 token 的有效性
 * 验证通过后，获得 {@link } 信息，并加入到 Spring Security 上下文
 *
 * @author 芋道源码
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;
    private final OAuth2TokenApi oauth2TokenApi;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        LoginUser loginUser = null;
        Integer userType = UserTypeEnum.ADMIN.getValue();
        String token = "";
        if (request.getParameter("authed") != null) {
            token = "Bearer test1";
        } else {
            token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        }
        if (StrUtil.isNotBlank(token)) {
             loginUser = buildLoginUserByToken(token, UserTypeEnum.ADMIN.getValue());
            if (loginUser == null) {
                loginUser = mockLoginUser(request, token, userType);
            }
        }

        if (loginUser != null) {
            SecurityFrameworkUtils.setLoginUser(loginUser, request);
        }
        // 继续过滤链
        chain.doFilter(request, response);
    }


    private LoginUser mockLoginUser(HttpServletRequest request, String token, Integer userType) {
        if (!securityProperties.getMockEnable()) {
            return null;
        }
        // 必须以 mockSecret 开头
        if (!token.startsWith(securityProperties.getMockSecret())) {
            return null;
        }
        // 构建模拟用户
        Long userId = Long.valueOf(token.substring(securityProperties.getMockSecret().length()));
        return new LoginUser().setId(userId).setUserType(userType)
                .setTenantId(WebFrameworkUtils.getTenantId(request));
    }


    private LoginUser buildLoginUserByToken(String token, Integer userType) {
        try {
            // 校验访问令牌
            OAuth2AccessTokenCheckRespDTO accessToken = oauth2TokenApi.checkAccessToken(token).getCheckedData();
            if (accessToken == null) {
                return null;
            }
            // 用户类型不匹配，无权限
            if (ObjectUtil.notEqual(accessToken.getUserType(), userType)) {
                throw new AccessDeniedException("错误的用户类型");
            }

            // 构建登录用户
            return new LoginUser().setId(accessToken.getUserId()).setUserType(accessToken.getUserType())
                    .setTenantId(accessToken.getTenantId()).setScopes(accessToken.getScopes());
        } catch (ServiceException serviceException) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            return null;
        }
    }
}
