package org.example.pickupanddelivery.controller;

import org.example.pickupanddelivery.model.Order;
import org.example.pickupanddelivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/schedule")
    public String showScheduleForm(Model model) {
        model.addAttribute("order", new Order());
        return "schedule";
    }

    @PostMapping("/schedule")
    public String saveSchedule(@ModelAttribute Order order) {
        orderService.saveOrder(order);
        return "redirect:/track";
    }

    @GetMapping("/track")
    public String trackOrders(
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) String customerName,
            Model model) {

        List<Order> orders;

        if (orderId != null) {
            // Search by order ID
            Order order = orderService.getOrderById(orderId);
            orders = order != null ? List.of(order) : List.of();
        } else if (customerName != null && !customerName.trim().isEmpty()) {
            // Search by customer name (case-insensitive)
            orders = orderService.getAllOrders().stream()
                    .filter(order -> order.getCustomerName() != null &&
                            order.getCustomerName().toLowerCase().contains(customerName.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            // Show all orders
            orders = orderService.getAllOrders();
        }

        model.addAttribute("orders", orders);
        return "track";
    }

    @GetMapping("/available-times")
    public ResponseEntity<List<LocalTime>> getAvailableTimes(@RequestParam String type, @RequestParam LocalDate date) {
        return ResponseEntity.ok(orderService.getAvailableTimes(date, type));
    }

}