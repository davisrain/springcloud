package com.dzy.service;

import com.dzy.pojo.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    int createPayment(Payment payment);
    Payment getPaymentById(Long id);
}
