# Spring Boot Load Balancer System

A simple load balancing system built with Spring Boot that distributes incoming requests across multiple API service instances using a Round Robin strategy.

## Project Structure
```
load-balancer-system/
├── api-service/       # REST API that can run on multiple ports (8081 and 8082)
└── load-balancer/     # Routes incoming requests across API instances
```

## Prerequisites

- Java 17+
- Maven (or use the included `mvnw` wrapper)

## How to Run

### Step 1 - Start API Service Instance 1
```powershell
cd api-service
.\mvnw spring-boot:run
```

### Step 2 - Start API Service Instance 2
Open a new terminal:
```powershell
cd api-service
.\mvnw spring-boot:run "-Dspring-boot.run.arguments=--server.port=8082"
```

### Step 3 - Start the Load Balancer
Open a new terminal:
```powershell
cd load-balancer
.\mvnw spring-boot:run
```

## Testing

Hit the load balancer repeatedly and observe the port alternating between 8081 and 8082:
```powershell
curl http://localhost:8080/api/hello
```

To test error handling, stop both API instances and hit the same endpoint — you will get a clean JSON error response instead of a white label error page.

## Configuration

Backend instances are configured in `load-balancer/src/main/resources/application.yaml`:
```yaml
loadbalancer:
  instances: http://localhost:8081,http://localhost:8082
  health-check:
    path: /api/health
    interval-ms: 15000
```

To add a third instance, start another API service on a new port and add it to the list.

## Assumptions

- All instances run on localhost with different ports
- Backend services are stateless
- All instances expose the same `/api/health` endpoint
