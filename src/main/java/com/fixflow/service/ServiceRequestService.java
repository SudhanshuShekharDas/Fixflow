package com.fixflow.service;

import com.fixflow.entity.ServiceRequest;
import com.fixflow.entity.Staff;
import com.fixflow.entity.User;
import com.fixflow.repository.ServiceRequestRepository;
import com.fixflow.repository.StaffRepository;
import com.fixflow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository requestRepo;
    private final UserRepository userRepo;
    private final StaffRepository staffRepo;

    public ServiceRequestService(ServiceRequestRepository requestRepo,
                                 UserRepository userRepo,
                                 StaffRepository staffRepo) {
        this.requestRepo = requestRepo;
        this.userRepo = userRepo;
        this.staffRepo = staffRepo;
    }

    public List<ServiceRequest> getAll() {
        return requestRepo.findAll();
    }

    public ServiceRequest getById(Long id) {
        return requestRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Request not found"));
    }

    public ServiceRequest create(ServiceRequest request) {
        request.setStatus("OPEN");
        request.setCreatedAt(LocalDateTime.now());
        request.setDeadline(calculateDeadline(request.getPriority()));

        if (request.getUser() != null && request.getUser().getId() != null) {
            User user = userRepo.findById(request.getUser().getId())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            request.setUser(user);
        }
        return requestRepo.save(request);
    }

    private static final Set<String> STATUSES = Set.of("OPEN", "ASSIGNED", "IN_PROGRESS", "RESOLVED");

    public ServiceRequest updateStatus(Long id, String status) {
        if (status == null || !STATUSES.contains(status.toUpperCase())) {
            throw new IllegalArgumentException("Status must be one of " + STATUSES);
        }
        status = status.toUpperCase();
        ServiceRequest request = getById(id);
        request.setStatus(status);
        if ("RESOLVED".equalsIgnoreCase(status)) {
            request.setResolvedAt(LocalDateTime.now());
        }
        return requestRepo.save(request);
    }

    public ServiceRequest assign(Long requestId, Long staffId) {
        ServiceRequest request = getById(requestId);
        Staff staff = staffRepo.findById(staffId)
                .orElseThrow(() -> new NoSuchElementException("Staff not found"));
        request.setStaff(staff);
        request.setStatus("ASSIGNED");
        return requestRepo.save(request);
    }

    public void delete(Long id) {
        requestRepo.deleteById(id);
    }

    private LocalDateTime calculateDeadline(String priority) {
        int hours = switch (priority == null ? "LOW" : priority.toUpperCase()) {
            case "HIGH" -> 4;
            case "MEDIUM" -> 12;
            default -> 24;
        };
        return LocalDateTime.now().plusHours(hours);
    }
}
