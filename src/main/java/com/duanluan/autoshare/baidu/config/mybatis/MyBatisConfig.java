package com.duanluan.autoshare.baidu.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置
 */
@MapperScan({"com.duanluan.**.mapper"})
@Configuration
public class MyBatisConfig {
}
