package com.dzy.service;

import com.dzy.pojo.CommonResult;
import com.dzy.pojo.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444, "openfeign fallback", new Payment(id, null));
    }
}
