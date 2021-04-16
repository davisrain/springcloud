package com.dzy.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "PaymentFallbackService paymentInfo_OK fallbcak method, 服务器忙，请稍后再试";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "PaymentFallbackService paymentInfo_timeout fallbcak method, 服务器忙，请稍后再试";
    }
}
