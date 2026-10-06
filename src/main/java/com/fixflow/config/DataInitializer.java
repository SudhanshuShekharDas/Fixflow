package com.fixflow.config;

import com.fixflow.entity.Staff;
import com.fixflow.entity.User;
import com.fixflow.repository.StaffRepository;
import com.fixflow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(UserRepository users, StaffRepository staff) {
        return args -> {
            if (users.count() == 0) {
                users.save(new User("Demo User", "user@fixflow.com", "USER"));
                users.save(new User("Admin", "admin@fixflow.com", "ADMIN"));
            }
            if (staff.count() == 0) {
                staff.save(new Staff("Rahul Kumar", "Electrical", "rahul@fixflow.com"));
                staff.save(new Staff("Anita Das", "Maintenance", "anita@fixflow.com"));
            }
        };
    }
}
