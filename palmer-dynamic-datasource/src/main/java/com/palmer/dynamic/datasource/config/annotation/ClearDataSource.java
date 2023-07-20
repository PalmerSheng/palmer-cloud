package com.palmer.dynamic.datasource.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加注解 清空数据源的 ThreadLocal
 *
 * @author palmer
 * @date 2022-10-14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ClearDataSource {
}
