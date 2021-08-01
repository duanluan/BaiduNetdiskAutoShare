package com.duanluan.autoshare.baidu.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分享记录
 */
@Data
public class ShareRecord implements Serializable {

  private static final long serialVersionUID = -4561850169329211991L;

  private Long id;
  /**
   * 分享时间，单位秒（s）
   */
  private LocalDateTime ctime;
  /**
   * 分享的文件 ID，[] 格式，逗号分隔
   */
  private String fsIds;
  /**
   * 失效类型（-1: 失效, 0: 有效, 1: 永久）
   */
  private Integer expiredType;
  /**
   * 失效时间，单位秒（s）
   */
  private LocalDateTime expiredTime;
  /**
   * 提取码
   */
  private String passwd;
  /**
   * 分享 ID
   */
  private Long shareId;
  /**
   * 分享链接
   */
  private String shortlink;
  /**
   * 分享 URL
   */
  private String shorturl;
  /**
   * 状态（0: 正常, 1: 已过期）
   */
  private Integer status;
  /**
   * 类别（-1: 文件夹, 1: 文件, 4: txt, 5: exe, 6: ttf）
   */
  private Integer typicalCategory;
  /**
   * 路径
   */
  private String typicalPath;
  /**
   * 浏览次数
   */
  private Integer vCnt;
  /**
   * 下载次数
   */
  private Integer dCnt;
  /**
   * 保存次数
   */
  private Integer tCnt;
}
