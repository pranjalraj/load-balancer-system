package com.example.load_balancer.service;

import com.example.load_balancer.model.ServiceInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstanceRegistry {

    @Value("${loadbalancer.instances}")
    private String instanceUrls;

    @Value("${loadbalancer.health-check.path}")
    private String healthCheckPath;

    private final RestTemplate restTemplate = new RestTemplate();
    private final List<ServiceInstance> instances = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (String url : instanceUrls.split(",")) {
            String[] parts = url.replace("http://", "").split(":");
            instances.add(new ServiceInstance(parts[0], Integer.parseInt(parts[1])));
        }
    }

    public List<ServiceInstance> getHealthyInstances() {
        return instances.stream()
                .filter(ServiceInstance::isHealthy)
                .toList();
    }

    public List<ServiceInstance> getAllInstances() {
        return instances;
    }

    public void checkHealth() {
        for (ServiceInstance instance : instances) {
            try {
                restTemplate.getForObject(instance.getBaseUrl() + healthCheckPath, String.class);
                instance.setHealthy(true);
            } catch (Exception e) {
                instance.setHealthy(false);
            }
        }
    }
}