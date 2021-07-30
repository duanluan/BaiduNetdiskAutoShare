package com.duanluan.autoshare.baidu.entity.ro;

import lombok.Data;

/**
 * 百度网盘 响应结果对象
 */
@Data
public class BaseRO {

  /**
   * 错误编号
   */
  private Integer errno;
  /**
   * 显示消息
   */
  private String showMsg;
}
