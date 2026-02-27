package com.example.load_balancer.service;

import com.example.load_balancer.model.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RoundRobinStrategy implements LoadBalancingStrategy{
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Optional<ServiceInstance> selectInstance(List<ServiceInstance> instances) {
        if(instances.isEmpty()) return Optional.empty();
        int index =  counter.getAndIncrement() % instances.size();
        return Optional.of(instances.get(index));
    }
}
