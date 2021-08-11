// package com.duanluan.autoshare.baidu.mq.consumer;
//
// import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
// import com.duanluan.autoshare.baidu.entity.Account;
// import com.duanluan.autoshare.baidu.entity.ShareRecord;
// import com.duanluan.autoshare.baidu.entity.ro.ShareSetRO;
// import com.duanluan.autoshare.baidu.enums.ExpiredTypeEnum;
// import com.duanluan.autoshare.baidu.mq.message.AutoShareMessage;
// import com.duanluan.autoshare.baidu.service.IAccountService;
// import com.duanluan.autoshare.baidu.service.IShareRecordService;
// import com.duanluan.autoshare.baidu.util.BaiduNetdiskUtils;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.commons.lang3.RandomStringUtils;
// import org.apache.commons.lang3.StringUtils;
// import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
// import org.apache.rocketmq.spring.core.RocketMQListener;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
//
// import java.util.List;
//
// @Slf4j
// @RocketMQMessageListener(
//   topic = AutoShareMessage.TOPIC,
//   consumerGroup = "BaiduNetdiskAutoShare-" + AutoShareMessage.TOPIC
// )
// @Component
// public class AutoShareConsumer implements RocketMQListener<AutoShareMessage> {
//
//   @Autowired
//   private IAccountService accountService;
//   @Autowired
//   private IShareRecordService shareRecordService;
//   @Autowired
//   private BaiduNetdiskUtils baiduNetdiskUtils;
//
//   @Override
//   public void onMessage(AutoShareMessage message) {
//     List<Long> fsIds = message.getFsIds();
//
//     List<Account> accountList = accountService.list();
//     Account account = accountList.get(0);
//
//     // 检查登录状态
//     if (!baiduNetdiskUtils.loginStatus(account.getCookie())) {
//       // TODO 邮件通知并停止定时任务
//       return;
//     }
//     // 检查数据库中是否存在失效链接且失效类型是否为失效
//     ShareRecord shareRecord = new ShareRecord();
//     shareRecord.setFsIds(StringUtils.join(fsIds, ","));
//     shareRecord.setExpiredType(ExpiredTypeEnum.INVALID.getValue());
//     if (shareRecordService.count(new QueryWrapper<>(shareRecord)) > 0) {
//       // 分享
//       String pwd = RandomStringUtils.randomAlphanumeric(4);
//       ShareSetRO shareSetRO = baiduNetdiskUtils.shareSet(account.getBdstoken(), account.getLogid(), pwd, fsIds);
//       // 已分享的更新数据
//       if (shareSetRO != null) {
//         shareRecord.setPasswd(pwd);
//         shareRecord.setVCnt(0);
//         shareRecord.setDCnt(0);
//         shareRecord.setTCnt(0);
//         shareRecord.setCtime(shareSetRO.getCtime());
//         shareRecord.setExpiredType(shareSetRO.getExpiredType());
//         shareRecord.setExpiredTime(shareSetRO.getExpiredTime());
//         shareRecord.setShareId(shareSetRO.getShareId());
//         shareRecord.setShortlink(shareSetRO.getLink());
//         shareRecord.setShorturl(shareSetRO.getShorturl());
//         shareRecordService.updateById(shareRecord);
//       }
//     }
//   }
// }
