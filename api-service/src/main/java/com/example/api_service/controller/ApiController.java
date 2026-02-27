package com.example.api_service.controller;

import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/api")
public class ApiController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> hello(){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from API service");
        response.put("port", serverPort);
        response.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.ok(response);

    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health(){
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("port", serverPort);

        return ResponseEntity.ok(response);
    }

}
