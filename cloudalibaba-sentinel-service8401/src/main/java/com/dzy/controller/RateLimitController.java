package com.dzy.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.dzy.handler.CustomBlockHandler;
import com.dzy.pojo.CommonResult;
import com.dzy.pojo.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult<Payment> byResource() {
        return new CommonResult<>(200, "按资源名称限流ok", new Payment(2020L, "serial001"));
    }

    public CommonResult handleException(BlockException ex) {
        return new CommonResult(444, ex.getClass().getCanonicalName() + "\t服务不可用");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl(){
        return new CommonResult(200, "按url限流测试ok", new Payment(2020L, "serial002"));
    }


    @GetMapping("/rateLimit/customBlockHandler")
    @SentinelResource(value = "customBlockHandler",
            blockHandlerClass = CustomBlockHandler.class,
            blockHandler = "globalBlockHandler2")
    public CommonResult customBlockHandler() {
        return new CommonResult(200, "按客户自定义", new Payment(2020L, "serial003"));
    }
}
