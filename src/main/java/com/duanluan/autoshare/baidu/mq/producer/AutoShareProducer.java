package com.duanluan.autoshare.baidu.mq.producer;

import com.duanluan.autoshare.baidu.mq.message.AutoShareMessage;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutoShareProducer {

  @Autowired
  private RocketMQTemplate rocketMqTemplate;

  public SendResult syncSend(List<Long> fsIds) {
    // 创建 AutoShareRecordMessage 消息
    AutoShareMessage message = new AutoShareMessage();
    message.setFsIds(fsIds);
    // 同步发送消息
    return rocketMqTemplate.syncSend(AutoShareMessage.TOPIC, message);
  }

  public void asyncSend(List<Long> fsIds, SendCallback callback) {
    // 创建 AutoShareRecordMessage 消息
    AutoShareMessage message = new AutoShareMessage();
    message.setFsIds(fsIds);
    // 异步发送消息
    rocketMqTemplate.asyncSend(AutoShareMessage.TOPIC, message, callback);
  }

  public void onewaySend(List<Long> fsIds) {
    // 创建 AutoShareRecordMessage 消息
    AutoShareMessage message = new AutoShareMessage();
    message.setFsIds(fsIds);
    // oneway 发送消息
    rocketMqTemplate.sendOneWay(AutoShareMessage.TOPIC, message);
  }
}
