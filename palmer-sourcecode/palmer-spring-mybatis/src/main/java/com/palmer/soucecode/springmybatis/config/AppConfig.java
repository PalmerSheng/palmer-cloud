package com.palmer.soucecode.springmybatis.config;

import com.palmer.soucecode.springmybatis.core.PalmerMapperScan;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author palmer
 * @date 2023-12-13
 */
@Configuration
@PalmerMapperScan("com.palmer.soucecode.springmybatis.mapper")
public class AppConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        InputStream inputstream =  Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory sqlSessionFactory  = new SqlSessionFactoryBuilder().build(inputstream);
        return sqlSessionFactory;
    }
}
