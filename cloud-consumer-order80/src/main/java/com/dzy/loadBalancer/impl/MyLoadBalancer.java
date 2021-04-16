package com.dzy.loadBalancer.impl;

import com.dzy.loadBalancer.LoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLoadBalancer implements LoadBalancer {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public int getAndIncrement() {
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while(!atomicInteger.compareAndSet(current, next));
        System.out.println("第几次调用接口:" + next);
        return next;
    }

    @Override
    public ServiceInstance getServiceInstance(List<ServiceInstance> instanceList) {
        int index = getAndIncrement() % instanceList.size();
        return instanceList.get(index);
    }
}
