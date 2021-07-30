package com.duanluan.autoshare.baidu.controller;

import com.duanluan.autoshare.baidu.common.BaseController;
import com.duanluan.autoshare.baidu.service.IShareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分享记录 Controller
 */
@RestController
public class ShareRecordController extends BaseController {

  @Autowired
  private IShareRecordService shareRecordService;
}
