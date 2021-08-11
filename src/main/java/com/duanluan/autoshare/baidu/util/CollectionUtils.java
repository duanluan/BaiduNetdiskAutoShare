package com.duanluan.autoshare.baidu.util;


public class CollectionUtils {

  private CollectionUtils(){
  }

  public static boolean sizeIsEmpty(Object object) {
    return org.apache.commons.collections4.CollectionUtils.sizeIsEmpty(object);
  }

  public static boolean sizeIsNotEmpty(Object object) {
    return !org.apache.commons.collections4.CollectionUtils.sizeIsEmpty(object);
  }
}
