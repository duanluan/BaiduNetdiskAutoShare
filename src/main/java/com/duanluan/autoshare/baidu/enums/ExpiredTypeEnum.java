package com.duanluan.autoshare.baidu.enums;

public enum ExpiredTypeEnum {
  /**
   * 失效的
   */
  INVALID(-1),
  /**
   * 有效的
   */
  VALID(0),
  /**
   * 永久的
   */
  PERMANENT(1),
  ;

  private Integer value;

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  ExpiredTypeEnum(Integer value) {
    this.value = value;
  }
}
