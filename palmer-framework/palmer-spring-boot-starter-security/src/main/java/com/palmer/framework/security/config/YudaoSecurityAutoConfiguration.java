package com.palmer.framework.security.config;

import com.palmer.framework.security.core.filter.MyFilter;
import com.palmer.framework.security.core.filter.TokenAuthenticationFilter;
import com.palmer.framework.web.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author palmer
 * @date 2023-11-15
 */
@Configuration
public class YudaoSecurityAutoConfiguration {
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}
