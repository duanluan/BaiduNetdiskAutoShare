package com.duanluan.autoshare.baidu.controller;

  import com.duanluan.autoshare.baidu.common.BaseController;
import com.duanluan.autoshare.baidu.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号 前端控制器
 *
 * @author duanluan
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {

  @Autowired
  private IAccountService accountService;

}
