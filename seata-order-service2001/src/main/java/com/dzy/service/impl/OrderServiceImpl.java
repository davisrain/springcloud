package com.dzy.service.impl;

import com.dzy.domain.Order;
import com.dzy.mapper.OrderMapper;
import com.dzy.service.AccountService;
import com.dzy.service.OrderService;
import com.dzy.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderMapper orderMapper;
    @Resource
    StorageService storageService;
    @Resource
    AccountService accountService;

    @Override
    public int create(Order order) {
        log.info("-------------> 创建订单");
        int i = orderMapper.create(order);
        log.info("-------------> 订单微服务开始调用库存，做扣减count");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("-------------> 订单微服务开始调用库存，做扣减end");

        log.info("-------------> 订单微服务开始调用账户，做扣减money");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("-------------> 订单微服务开始调用账户，做扣减end");

        log.info("-------------> 修改订单状态开始");
        orderMapper.updateStatus(order.getId(), 1);
        log.info("-------------> 修改订单状态结束");
        log.info("-------------> 创建订单结束");
        return i;
    }
}
