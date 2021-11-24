package com.example.demo.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Haoran.Hua
 * @Description: TODO
 * @date 2021/11/23 10:34 上午
 */
@Configuration
@MapperScan("com.example.demo.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
