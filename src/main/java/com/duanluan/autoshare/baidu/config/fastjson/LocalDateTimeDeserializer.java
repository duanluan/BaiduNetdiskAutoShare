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

  @Override
  public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
    Object valObj = parser.parse();
    // LocalDateTime
    if (valObj != null && LocalDateTime.class.getName().equals(type.getTypeName())) {
      // 时间戳
      if (valObj instanceof Integer) {
        Integer valInt = ((Integer) valObj).intValue();
        if (valInt.toString().length() == 10) {
          valObj = DateUtils.parseLocalDateTime(valInt);
        }
      }
    }
    return (T) valObj;
  }

  @Override
  public int getFastMatchToken() {
    return JSONToken.LITERAL_INT;
  }
}
