package com.palmer.soucecode.springmybatis.core;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author palmer
 * @date 2023-12-13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(PalmerImportConfigurer.class)
public @interface PalmerMapperScan {
    String value() default "";
}
