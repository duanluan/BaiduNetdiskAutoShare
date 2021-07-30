package com.duanluan.autoshare.baidu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账号
 *
 * @author duanluan
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Account implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  /**
   * 用户名
   */
  private String username;
  /**
   * 密码
   */
  private String password;
  /**
   * 邮箱
   */
  private String email;
  /**
   * 手机号码
   */
  @TableField("phone_number")
  private String phoneNumber;
  /**
   * Cookie
   */
  private String cookie;
<!-- -->
<!-- -->
}