package com.duanluan.autoshare.baidu.common;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 响应结果
 */
public class R extends HashMap<String, Object> {

  private static final long serialVersionUID = -3853197683928057068L;

  /**
   * 编码
   */
  public static final String CODE = "code";
  /**
   * 消息
   */
  public static final String MSG = "msg";
  /**
   * 内容
   */
  public static final String DATA = "data";

  private static R getResult(ResultEnum resultEnum) {
    R r = new R();
    r.put(CODE, resultEnum.getCode());
    r.put(MSG, resultEnum.getMsg());
    r.put(DATA, new ArrayList<>());
    return r;
  }

  protected static R getResult(ResultEnum resultEnum, Object data) {
    R r = getResult(resultEnum);
    r.put(DATA, data);
    return r;
  }

  public static R ok() {
    return getResult(ResultEnum.SUCCESS);
  }

  public static R ok(Object data) {
    return getResult(ResultEnum.SUCCESS, data);
  }

  public static R ok(ResultEnum resultEnum, Object data) {
    return getResult(resultEnum, data);
  }

  public static R error() {
    return getResult(ResultEnum.FAILURE);
  }

  public static R error(ResultEnum resultEnum) {
    return getResult(resultEnum);
  }

  public static R error(String msg) {
    return ResultEnum.FAILURE.customMsg(msg);
  }

  public static R errorDateMsg(String msg, String data, int code) {
    R r = new R();
    r.put(CODE, code);
    r.put(MSG, msg);
    r.put(DATA, data);
    return r;
  }

  public static R error(Integer code, String msg) {
    return ResultEnum.FAILURE.custom(code, msg);
  }

  public static R paramError() {
    return getResult(ResultEnum.PARAM_ERROR);
  }

  public static R paramError(String msg) {
    return ResultEnum.PARAM_ERROR.customMsg(msg);
  }

  public static R custom(boolean successful) {
    if (successful) {
      return R.ok();
    }
    return R.error();
  }

  /**
   * 自定义消息
   *
   * @param msg 消息
   * @return 响应枚举
   */
  public R customMsg(String msg) {
    this.put(MSG, msg);
    return this;
  }

  /**
   * 自定义消息前缀
   *
   * @param msg 消息
   * @return 响应枚举
   */
  public R customPrefixMsg(String msg) {
    this.put(MSG, msg + this.get(MSG));
    return this;
  }

  /**
   * 自定义消息后缀
   *
   * @param msg 消息
   * @return 响应枚举
   */
  public R customSuffixMsg(String msg) {
    this.put(MSG, this.get(MSG) + msg);
    return this;
  }

  /**
   * 自定义
   *
   * @param code 编码
   * @param msg  消息
   * @return 响应枚举
   */
  public R custom(int code, String msg) {
    this.put(CODE, code);
    this.put(MSG, msg);
    return this;
  }


  /**
   * 响应枚举，参考 docs/响应编码消息规范.xlsx
   */
  public enum ResultEnum {
    /**
     * 防止警告
     */
    SUCCESS(0x000001, "成功"),
    FAILURE(0x000000, "系统异常"),
    PARAM_ERROR(0x000101, "参数错误"),
    FOREIGN_DATA_EXISTENT(0x000201, "存在关联数据"),
    QUERY_FAILURE(0x000301, "查询失败"),
    SAVE_FAILURE(0x000302, "保存失败"),
    UPDATE_FAILURE(0x000303, "更新失败"),
    REMOVE_FAILURE(0x000304, "删除失败"),
    NO_COMPLIANCE_DATA(0x000305, "无符合数据"),
    ;

    /**
     * 编码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;

    ResultEnum(int code, String msg) {
      this.code = code;
      this.msg = msg;
    }

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }

    /**
     * 自定义消息
     *
     * @param msg 消息
     * @return 响应枚举
     */
    public R customMsg(String msg) {
      R r = new R();
      r.put(CODE, this.code);
      r.put(MSG, msg);
      r.put(DATA, null);
      return r;
    }

    /**
     * 自定义消息前缀
     *
     * @param msg 消息
     * @return 响应枚举
     */
    public R customPrefixMsg(String msg) {
      R r = new R();
      r.put(CODE, this.code);
      r.put(MSG, msg + getMsg());
      r.put(DATA, null);
      return r;
    }

    /**
     * 自定义消息后缀
     *
     * @param msg 消息
     * @return 响应枚举
     */
    public R customSuffixMsg(String msg) {
      R r = new R();
      r.put(CODE, this.code);
      r.put(MSG, getMsg() + msg);
      r.put(DATA, null);
      return r;
    }

    /**
     * 自定义
     *
     * @param code 编码
     * @param msg  消息
     * @return 响应枚举
     */
    public R custom(int code, String msg) {
      R r = new R();
      r.put(CODE, code);
      r.put(MSG, msg);
      r.put(DATA, null);
      return r;
    }
  }
}
