package com.palmer.biz.mybatisdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author palmer
 * @date 2023-06-19
 */
@SpringBootApplication
@Slf4j
public class MybatisDemoApplication {
    @PostConstruct
    public void test() {
        log.debug("my  debug log  test...");
        log.info("my  info log  test...");
        log.error("my  error log  test...");
    }

    public static void main(String[] args) {
        SpringApplication.run(MybatisDemoApplication.class, args);
    }
}
