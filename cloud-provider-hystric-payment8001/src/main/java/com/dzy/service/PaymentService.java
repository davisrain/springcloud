package com.dzy.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK, id：" + id + "\t" + "O(∩_∩)O哈哈";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_timeoutHandler", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_timeout(Integer id) {
//        int i = 10/0;
        int timeNumber = 5;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_timeout, id：" + id + "\t" + "O(∩_∩)O哈哈, 耗时（秒）："
                + timeNumber;
    }

    public String paymentInfo_timeoutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " 服务接口超时或异常 " + "\t" + "/(ㄒoㄒ)/~~";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_circuitBreakerHandler", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //最少请求次数
            //熔断器打开后开始尝试半开的时间间隔
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "100000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")    //失败率达到多少跳闸
    })
    public String paymentInfo_circuitBreaker(Integer id) {

       if(id < 0) {
           throw new RuntimeException("id不能为负数");
       }
       String serialNumber = IdUtil.simpleUUID();
       return Thread.currentThread().getName() + "\t" + "调用成功，流水号为：" + serialNumber;
    }

    public String paymentInfo_circuitBreakerHandler(Integer id) {
        return "调用失败，断路器开启";
    }
}
