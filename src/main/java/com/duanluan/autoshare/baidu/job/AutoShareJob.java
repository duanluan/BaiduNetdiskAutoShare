package com.duanluan.autoshare.baidu.job;

import com.duanluan.autoshare.baidu.entity.Account;
import com.duanluan.autoshare.baidu.entity.ShareRecord;
import com.duanluan.autoshare.baidu.enums.ExpiredTypeEnum;
import com.duanluan.autoshare.baidu.service.IAccountService;
import com.duanluan.autoshare.baidu.util.BaiduNetdiskUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自动分享，失效的链接会重新分享
 */
@Component
public class AutoShareJob {

  @Autowired
  private IAccountService accountService;
  @Autowired
  private BaiduNetdiskUtils baiduNetdiskUtils;

  @Scheduled(cron = "0 0 3 * * ?")
  public void run() {
    List<Account> accountList = accountService.list();
    Account account = accountList.get(0);

    // 检查登录状态
    if (!baiduNetdiskUtils.loginStatus(account.getCookie())) {
      // TODO 邮件通知
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
        // 重新分享
        String fsIds = shareRecord.getFsIds();


        System.out.println(shareRecord.toString());
      }
    }
  }
}
