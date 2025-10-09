package org.example.pickupanddelivery.controller;

import org.example.pickupanddelivery.model.Driver;
import org.example.pickupanddelivery.model.Message;
import org.example.pickupanddelivery.model.Order;
import org.example.pickupanddelivery.service.DriverService;
import org.example.pickupanddelivery.service.MessageService;
import org.example.pickupanddelivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DeliveryCoordinatorController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/coordinator")
    public String coordinatorDashboard(Model model, @RequestParam(required = false) String status) {
        List<Order> orders;
        if (status != null && !status.isEmpty()) {
            orders = orderService.getOrdersByStatus(status);
        } else {
            orders = orderService.getAllOrders();
        }

        List<Driver> drivers = driverService.getAllDrivers();
        List<Message> activeMessages = messageService.getActiveParentMessages();
        List<Message> unreadMessages = messageService.getAllUnreadMessages();

        // Calculate statistics in Java
        long pendingCount = orders.stream().filter(order -> "PENDING".equals(order.getStatus())).count();
        long inProgressCount = orders.stream().filter(order -> "IN_PROGRESS".equals(order.getStatus())).count();
        long readyCount = orders.stream().filter(order -> "READY".equals(order.getStatus())).count();

        model.addAttribute("orders", orders);
        model.addAttribute("drivers", drivers);
        model.addAttribute("status", status);
        model.addAttribute("activeMessages", activeMessages);
        model.addAttribute("unreadMessages", unreadMessages);
        model.addAttribute("unreadMessageCount", unreadMessages.size());
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("readyCount", readyCount);

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

    // Message endpoints
    @PostMapping("/sendMessageToDriver")
    public String sendMessageToDriver(@RequestParam Long driverId,
                                      @RequestParam String messageContent,
                                      @RequestParam String messageType,
                                      @RequestParam(required = false) Long orderId) {
        messageService.sendMessageToDriver(driverId, messageContent, messageType, orderId);
        return "redirect:/coordinator";
    }

    @PostMapping("/sendIssueToDriver")
    public String sendIssueToDriver(@RequestParam Long driverId,
                                    @RequestParam String issueDescription,
                                    @RequestParam(required = false) Long orderId) {
        messageService.sendIssueMessage(driverId, issueDescription, orderId);
        return "redirect:/coordinator";
    }

    @PostMapping("/replyToMessage/{messageId}")
    public String replyToMessage(@PathVariable Long messageId,
                                 @RequestParam String replyContent) {
        messageService.sendReplyToMessage(messageId, replyContent, "COORDINATOR_REPLY");
        return "redirect:/coordinator";
    }

    @PostMapping("/markMessageAsRead/{messageId}")
    public String markMessageAsRead(@PathVariable Long messageId) {
        messageService.markAsRead(messageId);
        return "redirect:/coordinator";
    }

    @PostMapping("/markMessageAsResolved/{messageId}")
    public String markMessageAsResolved(@PathVariable Long messageId) {
        messageService.markAsResolved(messageId);
        return "redirect:/coordinator";
    }
}