package com.palmer.framework.log.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;

import java.io.IOException;


/**
 * @author palmer
 * @date 2023-07-20
 */
@Configuration
@ConditionalOnClass({ LoggerContext.class, JoranConfigurator.class })
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class LogbackAutoConfiguration {

    @Value("${logging.config:classpath:logback-spring.xml}")
    private Resource logbackConfigurationFile;


    @Bean
    public LoggerContext loggerContext() {
        LoggerContext context = new LoggerContext();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        try {
            configurator.doConfigure(logbackConfigurationFile.getInputStream());
        } catch (JoranException | IOException e) {
            throw new IllegalStateException("Could not initialize Logback logging from " + logbackConfigurationFile, e);
        }
        return context;
    }

    @Bean
    @ConditionalOnMissingBean
    public LoggingSystem loggingSystem() {
        return new LogbackLoggingSystem(Thread.currentThread().getContextClassLoader());
    }



}