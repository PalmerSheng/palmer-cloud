package com.palmer.dynamic.datasource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author palmer
 * @date 2022-10-14
 */
@Configuration
public class DruidAutoConfiguration {
    @Autowired
    DynamicDataSourceProvider dynamicDataSourceProvider;

    @Bean
    DynamicDataSource dynamicDataSource() {
        return new DynamicDataSource(dynamicDataSourceProvider);
    }
}