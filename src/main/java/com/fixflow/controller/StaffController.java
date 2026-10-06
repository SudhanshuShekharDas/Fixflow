package com.fixflow.controller;

import com.fixflow.entity.Staff;
import com.fixflow.repository.StaffRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin
public class StaffController {
    private final StaffRepository repo;

    public StaffController(StaffRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Staff> all() {
        return repo.findAll();
    }
}
