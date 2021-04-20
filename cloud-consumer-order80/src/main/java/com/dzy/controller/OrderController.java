package com.dzy.controller;

import com.dzy.loadBalancer.LoadBalancer;
import com.dzy.pojo.CommonResult;
import com.dzy.pojo.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

//    private static final String PAYMENT_URL = "http://localhost:8001";
    private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancer loadBalancer;

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/zipkin")
    public String zipkin() {
       return restTemplate.getForObject(PAYMENT_URL + "/payment/zipkin", String.class);
    }

    @GetMapping("/consumer/payment/create")
    public CommonResult<Integer> createPayment(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        ServiceInstance serviceInstance = loadBalancer.getServiceInstance(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("/consumer/payment/createEntity")
    public CommonResult<Integer> createPaymentEntity(Payment payment) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        ObjectMapper objectMapper = new ObjectMapper();
        String params = objectMapper.writeValueAsString(payment);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(PAYMENT_URL + "/payment/create",
                httpEntity, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful())
            return entity.getBody();
        else
            return new CommonResult<Integer>(444, "插入失败", null);
    }

    @GetMapping("/consumer/payment/getEntity/{id}")
    public CommonResult<Payment> getPaymentEntityById(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id,
                CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful())
            return entity.getBody();
        else
            return new CommonResult<Payment>(444, "查询失败", null);

    }
}
