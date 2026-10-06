package com.fixflow.service;

import com.fixflow.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {
    private final ServiceRequestRepository repo;

    public DashboardService(ServiceRequestRepository repo) {
        this.repo = repo;
    }

    public Map<String, Long> stats() {
        Map<String, Long> data = new LinkedHashMap<>();
        data.put("total", repo.count());
        data.put("open", repo.countByStatus("OPEN"));
        data.put("assigned", repo.countByStatus("ASSIGNED"));
        data.put("inProgress", repo.countByStatus("IN_PROGRESS"));
        data.put("resolved", repo.countByStatus("RESOLVED"));
        data.put("highPriority", repo.countByPriority("HIGH"));
        return data;
    }
}
