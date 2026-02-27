package com.example.load_balancer.service;

import com.example.load_balancer.model.ServiceInstance;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LoadBalancerService {

    private final InstanceRegistry instanceRegistry;
    private final RoundRobinStrategy strategy;
    private final RestTemplate restTemplate = new RestTemplate();

    public LoadBalancerService(InstanceRegistry instanceRegistry, RoundRobinStrategy strategy) {
        this.instanceRegistry = instanceRegistry;
        this.strategy = strategy;
    }

    public ResponseEntity<String> route(String path, HttpMethod method, String body) {
        List<ServiceInstance> healthyInstances = instanceRegistry.getHealthyInstances();

        ServiceInstance instance = strategy.selectInstance(healthyInstances)
                .orElseThrow(() -> new RuntimeException("No healthy instances available"));

        String targetUrl = instance.getBaseUrl() + path;
        HttpEntity<String> entity = new HttpEntity<>(body);

        return restTemplate.exchange(targetUrl, method, entity, String.class);
    }
}