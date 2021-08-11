package com.duanluan.autoshare.baidu.entity.ro;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 取消分享 响应结果对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShareCancelRO extends BaseRO implements Serializable {

  private static final long serialVersionUID = 9098694971696879172L;

  private String errMsg;
  private String requestId;
}
