package com.github.mybatis.sql.config;

import com.github.mybatis.sql.interceptor.TestMapperQueryLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuxin 2021/6/3 6:37 下午
 */
@Configuration
public class MybatisLogConfig {

    @Bean
    public TestMapperQueryLogInterceptor testMapperLogInterceptor() {
        return new TestMapperQueryLogInterceptor();
    }
}
