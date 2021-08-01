package com.duanluan.autoshare.baidu.mq.message;

import lombok.Data;

@Data
public class AutoShareRecordMessage {

  public static final String TOPIC = "auto_share_record";

  /**
   * 分享的文件 ID
   */
  private String[] fsIds;
}
