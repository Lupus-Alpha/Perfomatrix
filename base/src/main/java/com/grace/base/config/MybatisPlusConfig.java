package com.grace.base.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.grace.base.mapper")
public class MybatisPlusConfig {
}
