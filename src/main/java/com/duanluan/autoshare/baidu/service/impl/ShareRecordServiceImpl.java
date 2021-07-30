package com.duanluan.autoshare.baidu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duanluan.autoshare.baidu.entity.ShareRecord;
import com.duanluan.autoshare.baidu.mapper.ShareRecordMapper;
import com.duanluan.autoshare.baidu.service.IShareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareRecordServiceImpl extends ServiceImpl<ShareRecordMapper, ShareRecord> implements IShareRecordService {

  @Autowired
  private ShareRecordMapper shareRecordMapper;
}
