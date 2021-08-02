package com.duanluan.autoshare.baidu.mq.message;

import lombok.Data;

import java.util.List;

@Data
public class AutoShareMessage {

  public static final String TOPIC = "autoShareRecord";

  /**
   * 分享的文件 ID
   */
  private List<Long> fsIds;
}
