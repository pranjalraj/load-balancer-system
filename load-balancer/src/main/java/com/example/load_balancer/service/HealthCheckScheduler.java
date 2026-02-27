package com.example.load_balancer.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckScheduler {

    private final InstanceRegistry instanceRegistry;

    public HealthCheckScheduler(InstanceRegistry instanceRegistry) {
        this.instanceRegistry = instanceRegistry;
    }

    @Scheduled(fixedDelayString = "${loadbalancer.health-check.interval-ms}")
    public void run() {
        instanceRegistry.checkHealth();
    }
}