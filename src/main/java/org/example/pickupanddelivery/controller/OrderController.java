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
    public String trackOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "track";
    }

    @GetMapping("/available-times")
    public ResponseEntity<List<LocalTime>> getAvailableTimes(@RequestParam String type, @RequestParam LocalDate date) {
        return ResponseEntity.ok(orderService.getAvailableTimes(date, type));
    }

}