package com.palmer.dynamic.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author palmer
 * @date 2022-10-14
 */

@ConfigurationProperties(prefix = "spring.datasource.dynamic")
@Data
public class DruidProperties {

    private String primary;

    private Map<String, Map<String, String>> datasource;

    public DruidDataSource dataSource(DruidDataSource datasource) {

        return datasource;
    }


}