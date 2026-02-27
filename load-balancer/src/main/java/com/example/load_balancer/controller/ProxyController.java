package com.example.load_balancer.controller;

import com.example.load_balancer.service.LoadBalancerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class ProxyController {

    private final LoadBalancerService loadBalancerService;

    public ProxyController(LoadBalancerService loadBalancerService) {
        this.loadBalancerService = loadBalancerService;
    }

    @RequestMapping("/**")
    public ResponseEntity<String> proxy(HttpServletRequest request) throws IOException {
        String path = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        return loadBalancerService.route(path, method, body.isEmpty() ? null : body);
    }
}