package com.dzy.service.impl;

import com.dzy.mapper.PaymentMapper;
import com.dzy.pojo.Payment;
import com.dzy.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentMapper paymentMapper;

    public int createPayment(Payment payment) {
        return paymentMapper.createPayment(payment);
    }
    public Payment getPaymentById(Long id) {
        return paymentMapper.getPaymentById(id);
    }
}
