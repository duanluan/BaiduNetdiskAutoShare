package com.duanluan.autoshare.baidu.config.mvc;

import com.alibaba.fastjson.parser.ParserConfig;
import com.duanluan.autoshare.baidu.config.fastjson.LocalDateTimeDeserializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 项目启动后：https://www.cnblogs.com/rollenholt/p/3612440.html
 *
 * @author duanluan
 */
@Component
public class ApplicationListenerImpl implements ApplicationListener<ContextRefreshedEvent> {

  /**
   * 当 ApplicationContext 初始化或刷新时
   *
   * @param event
   */
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // 系统两种容器：root application context 和项目名-servlet context ；
    if (event.getApplicationContext().getParent() == null) {
      // 配置 FastJSON 序列化和反序列化
      // SerializeConfig.getGlobalInstance().put(LocalDateTime.class, new LocalDateTimeSerializer());
      ParserConfig.getGlobalInstance().putDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
    }
  }
}
