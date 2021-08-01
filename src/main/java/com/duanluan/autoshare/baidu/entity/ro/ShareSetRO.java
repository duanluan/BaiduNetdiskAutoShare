package com.duanluan.autoshare.baidu.entity.ro;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 分享 响应结果对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShareSetRO extends BaseRO implements Serializable {

  private static final long serialVersionUID = -4757890332995470431L;

  private String link;
}
