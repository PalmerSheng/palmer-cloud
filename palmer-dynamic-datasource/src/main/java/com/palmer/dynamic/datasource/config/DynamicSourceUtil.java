package com.palmer.dynamic.datasource.config;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态切换数据源工具类
 * @author palmer
 * @date 2022-10-14
 */
@Slf4j

public class DynamicSourceUtil {
    public static void  switchDataSource(DataSourceTypeEnum type) {

        DynamicDataSourceContextHolder.setDataSourceType(type.name());

        log.debug("切换到数据库 {} ...",type.name());

    }
    public static void  switchDataSourceByName(String dbName) {
        DynamicDataSourceContextHolder.setDataSourceType(dbName);

    }

}
