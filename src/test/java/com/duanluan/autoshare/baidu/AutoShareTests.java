package com.duanluan.autoshare.baidu;

import com.duanluan.autoshare.baidu.job.AutoShareJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
class AutoShareTests {

  @Autowired
  private AutoShareJob autoShareJob;

  @Test
  void contextLoads() {
    autoShareJob.run();
  }
}
