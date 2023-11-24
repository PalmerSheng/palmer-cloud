package com.palmer.framework.security.config;

import com.palmer.framework.security.core.filter.TokenAuthenticationFilter;
import com.palmer.framework.security.core.handler.AccessDeniedHandlerImpl;
import com.palmer.framework.security.core.handler.AuthenticationEntryPointImpl;
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
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
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


}
