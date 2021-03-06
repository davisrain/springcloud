package com.dzy.controller;

import com.dzy.pojo.CommonResult;
import com.dzy.pojo.Payment;
import com.dzy.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Value("${server.port}")
    String serverPort;

    @PostMapping("/payment/create")
    public CommonResult<Integer> createPayment(@RequestBody Payment payment) {
        int result = paymentService.createPayment(payment);
        log.info("插入结果是：" + result);
        if(result > 0)
            return new CommonResult<>(200, "插入数据库成功,serverPort:" + serverPort, result);
        return new CommonResult<>(444, "插入数据库失败");
    }

    @RequestMapping(value = "/payment/get/{id}", method = RequestMethod.GET)
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询结果是：" + payment);
        if(payment != null)
            return new CommonResult<>(200, "查询成功,serverPort:" + serverPort, payment);
        return new CommonResult<>(500, "查询失败");
    }

    @GetMapping("/payment/lb")
    public String paymentLb() {
        return serverPort;
    }
}
