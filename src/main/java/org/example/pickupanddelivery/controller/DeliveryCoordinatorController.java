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
public class DeliveryCoordinatorController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DriverService driverService;

    @GetMapping("/coordinator")
    public String coordinatorDashboard(Model model, @RequestParam(required = false) String status) {
        List<Order> orders;
        if (status != null && !status.isEmpty()) {
            orders = orderService.getOrdersByStatus(status);
        } else {
            orders = orderService.getAllOrders();
        }

        List<Driver> drivers = driverService.getAllDrivers();
        model.addAttribute("orders", orders);
        model.addAttribute("drivers", drivers);
        model.addAttribute("status", status);
        return "coordinator";
    }

    @PostMapping("/assignPickupDriver/{orderId}")
    public String assignPickupDriver(@PathVariable Long orderId, @RequestParam Long driverId) {
        orderService.assignPickupDriver(orderId, driverId);
        return "redirect:/coordinator";
    }

    @PostMapping("/assignDeliveryDriver/{orderId}")
    public String assignDeliveryDriver(@PathVariable Long orderId, @RequestParam Long driverId) {
        orderService.assignDeliveryDriver(orderId, driverId);
        return "redirect:/coordinator";
    }

    @PostMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        orderService.updateStatus(id, status);
        return "redirect:/coordinator";
    }
}