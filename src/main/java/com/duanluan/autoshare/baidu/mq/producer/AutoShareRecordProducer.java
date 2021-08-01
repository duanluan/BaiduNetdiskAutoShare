package com.duanluan.autoshare.baidu.mq.producer;

import com.duanluan.autoshare.baidu.mq.message.AutoShareRecordMessage;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoShareRecordProducer {

  @Autowired
  private RocketMQTemplate rocketMqTemplate;

  public SendResult syncSend(String[] fsIds) {
    // 创建 AutoShareRecordMessage 消息
    AutoShareRecordMessage message = new AutoShareRecordMessage();
    message.setFsIds(fsIds);
    // 同步发送消息
    return rocketMqTemplate.syncSend(AutoShareRecordMessage.TOPIC, message);
  }

  public void asyncSend(String[] fsIds, SendCallback callback) {
    // 创建 AutoShareRecordMessage 消息
    AutoShareRecordMessage message = new AutoShareRecordMessage();
    message.setFsIds(fsIds);
    // 异步发送消息
    rocketMqTemplate.asyncSend(AutoShareRecordMessage.TOPIC, message, callback);
  }

  public void onewaySend(String[] fsIds) {
    // 创建 AutoShareRecordMessage 消息
    AutoShareRecordMessage message = new AutoShareRecordMessage();
    message.setFsIds(fsIds);
    // oneway 发送消息
    rocketMqTemplate.sendOneWay(AutoShareRecordMessage.TOPIC, message);
  }
}
