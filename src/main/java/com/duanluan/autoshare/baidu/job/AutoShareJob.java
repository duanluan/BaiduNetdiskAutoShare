package com.duanluan.autoshare.baidu.job;

import com.duanluan.autoshare.baidu.entity.Account;
import com.duanluan.autoshare.baidu.entity.ShareRecord;
import com.duanluan.autoshare.baidu.enums.ExpiredTypeEnum;
import com.duanluan.autoshare.baidu.mq.consumer.AutoShareRecordConsumer;
import com.duanluan.autoshare.baidu.mq.producer.AutoShareRecordProducer;
import com.duanluan.autoshare.baidu.service.IAccountService;
import com.duanluan.autoshare.baidu.util.BaiduNetdiskUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自动分享，失效的链接会重新分享
 */
@Slf4j
@Component
public class AutoShareJob {

  @Autowired
  private IAccountService accountService;
  @Autowired
  private BaiduNetdiskUtils baiduNetdiskUtils;
  @Autowired
  private AutoShareRecordProducer autoShareRecordProducer;
  @Autowired
  private AutoShareRecordConsumer autoShareRecordConsumer;

  @Scheduled(cron = "0 0 3 * * ?")
  public void get() {
    List<Account> accountList = accountService.list();
    Account account = accountList.get(0);

    // 检查登录状态
    if (!baiduNetdiskUtils.loginStatus(account.getCookie())) {
      // TODO 邮件通知并停止定时任务
      return;
    }

    // 获取分享记录
    List<ShareRecord> shareRecordList = baiduNetdiskUtils.shareRecord(1, 100);
    if (CollectionUtils.sizeIsEmpty(shareRecordList)) {
      return;
    }

    for (ShareRecord shareRecord : shareRecordList) {
      // 失效的链接
      if (ExpiredTypeEnum.INVALID.getValue().equals(shareRecord.getExpiredType())) {
        String fsIds = shareRecord.getFsIds();
        autoShareRecordProducer.asyncSend(
          // 去除分享 ID 的 []，并转换为数组
          fsIds.substring(1, fsIds.length() - 1).split(","), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
              log.info("[AutoShareRecordProducer] 发送失效链接的分享文件 ID [{}] 到 MQ 成功！", fsIds);
            }

            @Override
            public void onException(Throwable e) {
              log.error("[AutoShareRecordProducer] 发送失效链接的分享文件 ID [{}] 到 MQ 失败！", fsIds, e);
            }
          });
      }
    }
  }

  @Scheduled(cron = "0 0 4 * * ?")
  public void share() {
    // autoShareRecordConsumer.onMessage();
  }
}
