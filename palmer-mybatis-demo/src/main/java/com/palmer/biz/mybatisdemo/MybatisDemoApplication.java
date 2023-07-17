package com.palmer.biz.mybatisdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author palmer
 * @date 2023-06-19
 */
@SpringBootApplication
public class MybatisDemoApplication {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String value;

    @PostConstruct
    public void test() {
        System.out.println("123:"+value);
    }

    public static void main(String[] args) {
        SpringApplication.run(MybatisDemoApplication.class, args);
    }
}
