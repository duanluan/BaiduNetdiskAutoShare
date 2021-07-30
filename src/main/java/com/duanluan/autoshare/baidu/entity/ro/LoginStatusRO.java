package com.duanluan.autoshare.baidu.entity.ro;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 登录状态 响应结果对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginStatusRO extends BaseRO implements Serializable {

  private static final long serialVersionUID = 4927954579328508447L;
}
