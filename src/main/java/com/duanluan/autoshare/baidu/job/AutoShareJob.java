package com.duanluan.autoshare.baidu.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duanluan.autoshare.baidu.entity.Account;
import com.duanluan.autoshare.baidu.entity.ShareRecord;
import com.duanluan.autoshare.baidu.entity.ro.ShareSetRO;
import com.duanluan.autoshare.baidu.enums.ExpiredTypeEnum;
import com.duanluan.autoshare.baidu.service.IAccountService;
import com.duanluan.autoshare.baidu.service.IShareRecordService;
import com.duanluan.autoshare.baidu.util.BaiduNetdiskUtils;
import com.duanluan.autoshare.baidu.util.CollectionUtils;
import com.duanluan.autoshare.baidu.util.NumberUtils;
import com.duanluan.autoshare.baidu.util.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
  private IShareRecordService shareRecordService;
  // @Autowired
  // private AutoShareProducer autoShareProducer;

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

    List<ShareRecord> saveShareRecordList = new ArrayList<>();
    for (ShareRecord shareRecord : shareRecordList) {
      // 失效链接
      if (ExpiredTypeEnum.INVALID.getValue().equals(shareRecord.getExpiredType())) {
        // 失效链接是否已存在
        ShareRecord shareRecordQuerier = new ShareRecord();
        shareRecordQuerier.setShareId(shareRecord.getShareId());
        if (shareRecordService.getOne(new QueryWrapper<>(shareRecordQuerier)) != null) {
          // TODO 更新分享链接
        } else {
          String fsIdsStr = shareRecord.getFsIds();
          // 去除分享 ID 的 []，并转换为数组
          List<Long> fsIds = new ArrayList<>();
          for (String fsIdStr : fsIdsStr.substring(1, fsIdsStr.length() - 1).split(",")) {
            Long fsId = NumberUtils.parseLong(fsIdStr);
            if (fsId > 0) {
              fsIds.add(fsId);
            }
          }
          if (CollectionUtils.sizeIsEmpty(fsIds)) {
            continue;
          }
          // 将分享链接存储到数据库
          Collections.sort(fsIds);
          shareRecord.setFsIds(StringUtils.join(fsIds, ","));
          saveShareRecordList.add(shareRecord);
          // if (shareRecordService.save(shareRecord)) {
          //   // 将失效链接发送到 MQ
          //   autoShareProducer.asyncSend(fsIds, new SendCallback() {
          //     @Override
          //     public void onSuccess(SendResult sendResult) {
          //       log.info("[AutoShareRecordProducer] 发送分享文件 ID [{}] 的失效链接到 MQ 成功！", fsIdsStr);
          //     }
          //
          //     @Override
          //     public void onException(Throwable e) {
          //       log.error("[AutoShareRecordProducer] 发送分享文件 ID [{}] 的失效链接到 MQ 失败！", fsIdsStr, e);
          //     }
          //   });
          // }
        }
      }
    }
    shareRecordService.saveBatch(saveShareRecordList);

    List<ShareRecord> updateShareRecordList = new ArrayList<>();
    // 获取已失效的分享链接
    ShareRecord shareRecordQuerier = new ShareRecord();
    shareRecordQuerier.setExpiredType(ExpiredTypeEnum.INVALID.getValue());
    for (ShareRecord shareRecord : shareRecordService.list(new QueryWrapper<>(shareRecordQuerier))) {
      // 取消分享原链接
      baiduNetdiskUtils.shareCancel(account.getBdstoken(), Collections.singletonList(shareRecord.getShareId().toString()));
      // 分享
      String pwd = RandomStringUtils.randomAlphanumeric(4);
      // ShareSetRO shareSetRO = baiduNetdiskUtils.shareSet(account.getBdstoken(), account.getLogid(), pwd, Arrays.stream(shareRecord.getFsIds().split(",")).map(NumberUtils::parseLong).collect(Collectors.toList()));
      ShareSetRO shareSetRO = new ShareSetRO();
      // 分享成功后
      if (shareSetRO != null) {
        // 更新已分享的
        shareRecord.setPasswd(pwd);
        shareRecord.setVCnt(0);
        shareRecord.setDCnt(0);
        shareRecord.setTCnt(0);
        shareRecord.setCtime(shareSetRO.getCtime());
        shareRecord.setExpiredType(shareSetRO.getExpiredType());
        shareRecord.setExpiredTime(shareSetRO.getExpiredTime());
        shareRecord.setShareId(shareSetRO.getShareId());
        shareRecord.setShortlink(shareSetRO.getLink());
        shareRecord.setShorturl(shareSetRO.getShorturl());
        updateShareRecordList.add(shareRecord);
      }
    }
    shareRecordService.updateBatchById(updateShareRecordList);
  }
}
