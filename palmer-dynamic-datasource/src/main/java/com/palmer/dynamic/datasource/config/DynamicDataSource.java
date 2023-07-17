package com.palmer.dynamic.datasource.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author palmer
 * @date 2022-10-13
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    DynamicDataSourceProvider dynamicDataSourceProvider;

    public DynamicDataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
        this.dynamicDataSourceProvider = dynamicDataSourceProvider;
        Map<Object, Object> targetDataSources = new HashMap<>(dynamicDataSourceProvider.loadDataSources());
        super.setTargetDataSources(targetDataSources);
        super.setDefaultTargetDataSource(dynamicDataSourceProvider.loadDataSources().get(DynamicDataSourceProvider.DEFAULT_DATASOURCE));
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceType = DynamicDataSourceContextHolder.getDataSourceType();
        log.debug("datasource: {} threadName：{}", dataSourceType,Thread.currentThread().getName());
      /*  if (!StringUtils.hasText(dataSourceType)) {
            // 指定默认数据源
            return DataSourceTypeEnum.db1.name();
        }*/
        return dataSourceType;
    }
}