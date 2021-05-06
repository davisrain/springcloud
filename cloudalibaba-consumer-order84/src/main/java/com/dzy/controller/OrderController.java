package com.dzy.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.dzy.pojo.CommonResult;
import com.dzy.pojo.Payment;
import com.dzy.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderController {

    private static final String URL = "http://cloudalibaba-payment-service";

    @Resource
    RestTemplate restTemplate;

    @GetMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback", fallback = "handlerFallback")
//    @SentinelResource(value = "fallback", blockHandler = "blockHandler")    //blockHandler只负责sentinel控制台的配置违规，不管java异常
    @SentinelResource(value = "fallback", blockHandler = "blockHandler", fallback = "handlerFallback",
            exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(URL + "/paymentSQL/" + id, CommonResult.class, id);
        if(id == 4)
            throw new IllegalArgumentException("IllegalArgumentException, 非法参数异常...");
        else if (result.getData() == null)
            throw new NullPointerException("NullPointerException, 该ID没有对应的记录");
        return result;
    }

    public CommonResult<Payment> blockHandler(@PathVariable Long id, BlockException e) {
        Payment payment = new Payment(id, null);
        return new CommonResult<>(445,"sentinel降级", payment);
    }

    public CommonResult<Payment> handlerFallback(@PathVariable Long id, Throwable t) {
        Payment payment = new Payment(id, null);
        return new CommonResult<>(444, "兜底方法handlerFallback, exception内容为：" + t.getMessage(), payment);
    }

    @Resource
    PaymentService paymentService;

    @GetMapping("/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
       return paymentService.paymentSQL(id);
    }

}
