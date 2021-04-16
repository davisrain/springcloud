package com.dzy.mapper;

import com.dzy.pojo.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentMapper {
    int createPayment(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}
