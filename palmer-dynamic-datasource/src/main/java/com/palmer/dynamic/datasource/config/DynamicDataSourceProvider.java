package com.palmer.dynamic.datasource.config;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author palmer
 * @date 2022-10-14
 */
public interface DynamicDataSourceProvider {
    String DEFAULT_DATASOURCE = "db1";
    /**
     * 加载所有的数据源
     * @return
     */
    Map<String, DataSource> loadDataSources();
}