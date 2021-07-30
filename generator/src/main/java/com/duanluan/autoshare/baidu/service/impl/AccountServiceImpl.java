package com.duanluan.autoshare.baidu.service.impl;

import com.duanluan.autoshare.baidu.entity.Account;
import com.duanluan.autoshare.baidu.mapper.AccountMapper;
import com.duanluan.autoshare.baidu.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 账号 服务实现
 *
 * @author duanluan
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

  @Autowired
  private AccountMapper accountMapper;

}
