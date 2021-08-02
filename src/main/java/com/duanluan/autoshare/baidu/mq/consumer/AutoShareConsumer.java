package com.duanluan.autoshare.baidu.mq.consumer;

import com.duanluan.autoshare.baidu.entity.Account;
import com.duanluan.autoshare.baidu.mq.message.AutoShareMessage;
import com.duanluan.autoshare.baidu.service.IAccountService;
import com.duanluan.autoshare.baidu.util.BaiduNetdiskUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RocketMQMessageListener(
  topic = AutoShareMessage.TOPIC,
  consumerGroup = "BaiduNetdiskAutoShare-" + AutoShareMessage.TOPIC
)
@Component
public class AutoShareConsumer implements RocketMQListener<AutoShareMessage> {

  @Autowired
  private IAccountService accountService;
  @Autowired
  private BaiduNetdiskUtils baiduNetdiskUtils;

  @Override
  public void onMessage(AutoShareMessage message) {
    List<Account> accountList = accountService.list();
    Account account = accountList.get(0);

    // 检查登录状态
    if (!baiduNetdiskUtils.loginStatus(account.getCookie())) {
      // TODO 邮件通知并停止定时任务
      return;
    }

    baiduNetdiskUtils.shareSet(account.getBdstoken(), account.getLogid(), RandomStringUtils.randomAlphanumeric(4), message.getFsIds());
  }
}
