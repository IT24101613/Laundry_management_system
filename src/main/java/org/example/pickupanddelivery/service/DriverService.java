package org.example.pickupanddelivery.service;

import org.example.pickupanddelivery.model.Driver;
import org.example.pickupanddelivery.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    // Saves a new driver
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    // Gets all drivers
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Gets a driver by ID
    public Driver getDriverById(Long id) {
        return driverRepository.findById(id).orElse(null);
    }

    // Gets the total number of drivers
    public long getDriverCount() {
        return driverRepository.count();
    }

    // Add test drivers on startup
    @PostConstruct
    public void initDrivers() {
        if (getAllDrivers().isEmpty()) {
            Driver driver1 = new Driver();
            driver1.setName("Driver1");
            driver1.setContact("123456");
            saveDriver(driver1);

            Driver driver2 = new Driver();
            driver2.setName("Driver2");
            driver2.setContact("789012");
            saveDriver(driver2);
        }
    }
}