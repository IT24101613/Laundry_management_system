package com.pgno209.smartwash.controller;

import com.pgno209.smartwash.model.Customer;
import com.pgno209.smartwash.model.OrderItem;
import com.pgno209.smartwash.model.Orders;
import com.pgno209.smartwash.service.OrdersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrdersService orderService;

    @GetMapping("/orders")
    public String showOrders(Model model) {
        List<Orders> pending = orderService.getPendingOrders();
        List<Orders> completed = orderService.getCompletedOrders();
        model.addAttribute("pendingOrders", pending);
        model.addAttribute("completedOrders", completed);
        return "orders";
    }

    @GetMapping("/order/form")
    public String showOrderForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("order", new Orders());
        return "orderForm";
    }

    @PostMapping("/order/save")
    public String saveOrder(
            @ModelAttribute Customer customer,
            @ModelAttribute Orders order,
            @RequestParam("pickupDatetimeStr") String pickupDatetimeStr,
            @RequestParam("deliveryDatetimeStr") String deliveryDatetimeStr,
            @RequestParam(value = "specialRequests", required = false) String specialRequests,
            HttpSession session) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        order.setPickupDatetime(LocalDateTime.parse(pickupDatetimeStr, formatter));
        order.setDeliveryDatetime(LocalDateTime.parse(deliveryDatetimeStr, formatter));

        @SuppressWarnings("unchecked")
        List<OrderItem> items = (List<OrderItem>) session.getAttribute("cart");
        orderService.saveOrder(customer, order, specialRequests, items);

        // Clear cart after save
        session.removeAttribute("cart");

        return "redirect:/order/success?orderId=" + order.getOrderId();
    }

    @GetMapping("/order/success")
    public String showSuccess(@RequestParam Integer orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "success";
    }
}