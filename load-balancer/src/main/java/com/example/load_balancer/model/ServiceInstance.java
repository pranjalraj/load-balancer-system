package com.example.load_balancer.model;

public class ServiceInstance {

    private final String host;
    private final int port;
    private boolean healthy = true;

    public ServiceInstance(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getBaseUrl() {
        return "http://" + host + ":" + port;
    }

    public String getId() {
        return host + ":" + port;
    }

    public String getHost() { return host; }
    public int getPort() { return port; }
    public boolean isHealthy() { return healthy; }
    public void setHealthy(boolean healthy) { this.healthy = healthy; }
}