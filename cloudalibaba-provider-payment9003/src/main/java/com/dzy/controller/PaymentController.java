package com.dzy.controller;

import com.dzy.pojo.CommonResult;
import com.dzy.pojo.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    @Value("${server.port}")
    String serverPort;

    private static Map<Long, Payment> map = new HashMap<>();
    static {
        map.put(1L, new Payment(1L, "aaaaaaaaa"));
        map.put(2L, new Payment(2L, "bbbbbbbbb"));
        map.put(3L, new Payment(3L, "ccccccccc"));
    }


    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        Payment payment = map.get(id);
        return new CommonResult<>(200, "from mysql, serverPort: " + serverPort, payment);
    }
}
