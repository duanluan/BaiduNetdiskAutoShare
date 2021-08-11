package com.duanluan.autoshare.baidu.entity.ro;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分享 响应结果对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShareSetRO extends BaseRO implements Serializable {

  private static final long serialVersionUID = -4757890332995470431L;

  /**
   * 分享时间，单位秒（s）
   */
  private LocalDateTime ctime;
  /**
   * 失效类型（-1: 失效, 0: 有效, 1: 永久）
   */
  private Integer expiredType;
  /**
   * 失效时间，单位秒（s）
   */
  private LocalDateTime expiredTime;
  /**
   * 分享 ID
   */
  private Long shareId;
  /**
   * 分享链接
   */
  private String link;
  /**
   * 分享 URL
   */
  private String shorturl;
}
