package com.dzy.service.impl;

import com.dzy.mapper.AccountMapper;
import com.dzy.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    AccountMapper accountMapper;

    @Override
    public int decrease(Long userId, BigDecimal money) {
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return accountMapper.decrease(userId, money);
    }
}
