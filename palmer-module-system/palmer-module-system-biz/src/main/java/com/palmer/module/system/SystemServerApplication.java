package com.palmer.module.system;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

/**
 * 项目的启动类
 *
 * 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章
 * 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章
 * 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章
 *
 * @author 芋道源码
 */
@SpringBootApplication
public class SystemServerApplication {

    public static void main(String[] args) {
        // 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章
        // 如果你碰到 启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章
        // 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章

        ConfigurableApplicationContext context = SpringApplication.run(SystemServerApplication.class, args);

        // 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章
        // 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章
        // 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章




        Multimap<HttpMethod, String> result = HashMultimap.create();
        // 获得接口对应的 HandlerMethod 集合
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                context.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // 获得有 @PermitAll 注解的接口
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            RequestMappingInfo key = entry.getKey();
            HandlerMethod value = entry.getValue();
            if (entry.getKey().getPathPatternsCondition() != null) {
                System.out.println(entry.getKey().getPathPatternsCondition().getPatterns());
            }
        }

    }

}
