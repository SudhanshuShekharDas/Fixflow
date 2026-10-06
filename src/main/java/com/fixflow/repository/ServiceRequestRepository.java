package com.fixflow.repository;

import com.fixflow.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    long countByStatus(String status);
    long countByPriority(String priority);
    List<ServiceRequest> findByStatusOrderByCreatedAtDesc(String status);
}
