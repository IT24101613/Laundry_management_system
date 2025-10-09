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
public class DriverController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/driver")
    public String driverDashboard(@RequestParam Long driverId, Model model) {
        List<Order> orders = orderService.getOrdersForDriver(driverId);
        Driver driver = driverService.getDriverById(driverId);
        List<Message> activeMessages = messageService.getActiveParentMessagesForDriver(driverId);
        List<Message> unreadMessages = messageService.getUnreadMessagesForDriver(driverId);

        // Calculate statistics in Java
        long pendingActions = orders.stream()
                .filter(order -> "PICKUP_ASSIGNED".equals(order.getStatus()) || "DELIVERY_ASSIGNED".equals(order.getStatus()))
                .count();

        long completed = orders.stream()
                .filter(order -> "PICKED_UP".equals(order.getStatus()) || "DELIVERED".equals(order.getStatus()))
                .count();

        model.addAttribute("orders", orders);
        model.addAttribute("driver", driver);
        model.addAttribute("activeMessages", activeMessages);
        model.addAttribute("unreadMessages", unreadMessages);
        model.addAttribute("unreadMessageCount", unreadMessages.size());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("pendingActionsCount", pendingActions);
        model.addAttribute("completedCount", completed);

        return "driver";
    }

    @PostMapping("/driver/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status, @RequestParam Long driverId) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            if ("PICKED_UP".equals(status) && order.getPickupDriver() != null && order.getPickupDriver().getId().equals(driverId)) {
                orderService.updateStatus(id, status);
            } else if ("DELIVERED".equals(status) && order.getDeliveryDriver() != null && order.getDeliveryDriver().getId().equals(driverId)) {
                orderService.updateStatus(id, status);
            }
        }
        return "redirect:/driver?driverId=" + driverId;
    }

    @PostMapping("/driver/markMessageAsRead/{messageId}")
    public String driverMarkMessageAsRead(@PathVariable Long messageId, @RequestParam Long driverId) {
        messageService.markAsRead(messageId);
        return "redirect:/driver?driverId=" + driverId;
    }

    @PostMapping("/driver/sendMessageToCoordinator")
    public String sendMessageToCoordinator(@RequestParam Long driverId,
                                           @RequestParam String messageContent,
                                           @RequestParam(required = false) Long orderId) {
        messageService.sendDriverMessageToCoordinator(driverId, messageContent, orderId);
        return "redirect:/driver?driverId=" + driverId;
    }

    @PostMapping("/driver/replyToMessage/{messageId}")
    public String driverReplyToMessage(@PathVariable Long messageId,
                                       @RequestParam Long driverId,
                                       @RequestParam String replyContent) {
        messageService.sendDriverReplyToMessage(messageId, replyContent);
        return "redirect:/driver?driverId=" + driverId;
    }
}