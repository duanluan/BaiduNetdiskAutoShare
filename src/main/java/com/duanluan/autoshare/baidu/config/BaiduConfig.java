package com.duanluan.autoshare.baidu.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "baidu")
@PropertySource("classpath:application.yml")
public class BaiduConfig {

  @Value("${app-id}")
  private String appId;
  private String channel;
  private String web;
  private String clienttype;
}
