package org.example.pickupanddelivery.controller;

import org.example.pickupanddelivery.model.Driver;
import org.example.pickupanddelivery.model.Order;
import org.example.pickupanddelivery.service.DriverService;
import org.example.pickupanddelivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DriverController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DriverService driverService;

    // Driver dashboard: /driver?driverId=1 (replace 1 with actual ID from DB)
    @GetMapping("/driver")
    public String driverDashboard(@RequestParam Long driverId, Model model) {
        List<Order> orders = orderService.getOrdersForDriver(driverId);
        Driver driver = driverService.getDriverById(driverId);
        model.addAttribute("orders", orders);
        model.addAttribute("driver", driver);
        return "driver";  // New driver
    }

    // Driver updates status
    @PostMapping("/driver/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status, @RequestParam Long driverId) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            // Simple check: Only update if assigned to this driver
            if ("PICKED_UP".equals(status) && order.getPickupDriver() != null && order.getPickupDriver().getId().equals(driverId)) {
                orderService.updateStatus(id, status);
            } else if ("DELIVERED".equals(status) && order.getDeliveryDriver() != null && order.getDeliveryDriver().getId().equals(driverId)) {
                orderService.updateStatus(id, status);
            }
        }
        return "redirect:/driver?driverId=" + driverId;
    }
}