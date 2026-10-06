package com.fixflow.controller;

import com.fixflow.entity.ServiceRequest;
import com.fixflow.service.ServiceRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin
public class ServiceRequestController {

    private final ServiceRequestService service;

    public ServiceRequestController(ServiceRequestService service) {
        this.service = service;
    }

    @GetMapping
    public List<ServiceRequest> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ServiceRequest one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ServiceRequest create(@RequestBody ServiceRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}/status")
    public ServiceRequest status(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.updateStatus(id, body.get("status"));
    }

    @PutMapping("/{id}/assign/{staffId}")
    public ServiceRequest assign(@PathVariable Long id, @PathVariable Long staffId) {
        return service.assign(id, staffId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
