package com.duanluan.autoshare.baidu.job;

import com.duanluan.autoshare.baidu.entity.Account;
import com.duanluan.autoshare.baidu.entity.ShareRecord;
import com.duanluan.autoshare.baidu.enums.ExpiredTypeEnum;
import com.duanluan.autoshare.baidu.mq.consumer.AutoShareConsumer;
import com.duanluan.autoshare.baidu.mq.producer.AutoShareProducer;
import com.duanluan.autoshare.baidu.service.IAccountService;
import com.duanluan.autoshare.baidu.util.BaiduNetdiskUtils;
import com.duanluan.autoshare.baidu.util.NumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
  private AutoShareProducer autoShareProducer;
  @Autowired
  private AutoShareConsumer autoShareConsumer;

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
        String fsIdsStr = shareRecord.getFsIds();
        // 去除分享 ID 的 []，并转换为数组
        List<Long> fsIds = new ArrayList<>();
        for (String fsIdStr : fsIdsStr.substring(1, fsIdsStr.length() - 1).split(",")) {
          Long fsId = NumberUtils.parseLong(fsIdStr);
          if (fsId > 0) {
            fsIds.add(fsId);
          }
        }
        autoShareProducer.asyncSend(fsIds, new SendCallback() {
          @Override
          public void onSuccess(SendResult sendResult) {
            log.info("[AutoShareRecordProducer] 发送失效链接的分享文件 ID [{}] 到 MQ 成功！", fsIdsStr);
          }
          @Override
          public void onException(Throwable e) {
            log.error("[AutoShareRecordProducer] 发送失效链接的分享文件 ID [{}] 到 MQ 失败！", fsIdsStr, e);
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
