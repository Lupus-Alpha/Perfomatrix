package com.grace.usermanagement.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.grace.usermanagement.mapper")
public class MybatisPlusConfig {
}
