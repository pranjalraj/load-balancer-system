package com.example.load_balancer.service;

import com.example.load_balancer.model.ServiceInstance;
import java.util.List;
import java.util.Optional;

public interface LoadBalancingStrategy {
    Optional<ServiceInstance> selectInstance(List<ServiceInstance> instances);
}
