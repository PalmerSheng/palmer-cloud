package com.palmer.soucecode.springmybatis.test;

import com.palmer.soucecode.springmybatis.config.AppConfig;
import com.palmer.soucecode.springmybatis.mapper.UserMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author palmer
 * @date 2023-12-13
 */
public class TestClass {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserMapper bean = context.getBean(UserMapper.class);
        System.out.println(bean.getUser());
    }
}
