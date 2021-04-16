package com.dzy.loadBalancer;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {

    ServiceInstance getServiceInstance(List<ServiceInstance> instanceList);
}
