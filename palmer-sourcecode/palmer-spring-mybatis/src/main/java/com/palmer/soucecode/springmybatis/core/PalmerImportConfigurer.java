package com.palmer.soucecode.springmybatis.core;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author palmer
 * @date 2023-12-13
 */
public class PalmerImportConfigurer implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(PalmerMapperScan.class.getName());
        String path = (String) annotationAttributes.get("value");
        PalmerScanner palmerScanner = new PalmerScanner(registry);
        // 这里添加自定义IncludeFilter 直接返回true ,过掉生成BeanDefinition限制
        palmerScanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        palmerScanner.scan(path);
    }
}
