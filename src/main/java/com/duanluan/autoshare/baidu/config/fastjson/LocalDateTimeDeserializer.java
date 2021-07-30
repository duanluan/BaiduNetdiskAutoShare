package com.duanluan.autoshare.baidu.config.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.duanluan.autoshare.baidu.util.DateUtils;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * 自定义反序列化：https://github.com/alibaba/fastjson/wiki/ObjectDeserializer_cn
 *
 * @author duanluan
 */
public class LocalDateTimeDeserializer implements ObjectDeserializer {

  /**
   * 反序列化
   *
   * @param parser    解析器，可以获取需要解析的值
   * @param type      反序列化后的类型
   * @param fieldName
   * @param <T>       反序列化后的类型类
   * @return 反序列化后的值
   */
  @Override
  public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
    Object valObj = parser.parse();
    // LocalDateTime
    if (valObj != null && LocalDateTime.class.getName().equals(type.getTypeName())) {
      // 时间戳
      if (valObj instanceof Integer) {
        long valInt = ((Integer) valObj).longValue();
        // 10 位时间戳
        if (Long.toString(valInt).length() == 10) {
          valObj = DateUtils.parseLocalDateTime(valInt * 1000);
          return (T) valObj;
        }
        return null;
      }
    }
    return (T) valObj;
  }

  @Override
  public int getFastMatchToken() {
    return JSONToken.LITERAL_INT;
  }
}
