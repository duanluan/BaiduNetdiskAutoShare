package com.duanluan.autoshare.baidu;

import com.duanluan.autoshare.baidu.job.AutoShareJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
// 开启定时任务
@EnableScheduling
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

    AutoShareJob autoShareJob = context.getBean("autoShareJob",AutoShareJob.class);
    autoShareJob.get();
  }
}
