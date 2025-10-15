package com.janiya.reviews_app_springboot.service;

import com.janiya.reviews_app_springboot.model.User;
import com.janiya.reviews_app_springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initDefaultUsers() {
        // Check if default users already exist
        if (userRepository.count() == 0) {
            // Add some default users for testing
            userRepository.save(new User("customer1", "password", "customer1@example.com", "customer"));
            userRepository.save(new User("customer2", "password", "customer2@example.com", "customer"));
            userRepository.save(new User("admin", "password", "admin@example.com", "admin"));
            userRepository.save(new User("admin1", "password", "admin1@example.com", "admin"));
            userRepository.save(new User("superadmin", "password", "superadmin@example.com", "admin"));
            
            // Debug: Print all created users
            System.out.println("Created default users:");
            for (User user : userRepository.findAll()) {
                System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole());
            }
        } else {
            System.out.println("Users already exist in database, skipping default user creation");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        System.out.println("UserService: Deleting user with ID: " + id);
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                System.out.println("UserService: User deleted successfully");
            } else {
                System.out.println("UserService: User with ID " + id + " not found");
            }
        } catch (Exception e) {
            System.out.println("UserService: Error deleting user: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}