package com.pgno209.smartwash.controller;

import com.pgno209.smartwash.model.Customer;
import com.pgno209.smartwash.model.OrderItem;
import com.pgno209.smartwash.model.Orders;
import com.pgno209.smartwash.model.SpecialRequest;
import com.pgno209.smartwash.service.OrdersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrdersService orderService;

    @GetMapping("/orders")
    public String showOrders(Model model) {
        List<Orders> current = orderService.getCurrentOrders();
        List<Orders> completed = orderService.getCompletedOrders();
        model.addAttribute("currentOrders", current);
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
            @Valid @ModelAttribute("order") Orders order,
            BindingResult orderResult,
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult customerResult,
            @RequestParam(value = "specialRequests", required = false) String specialRequests,
            HttpSession session,
            Model model) {

        // Check for validation errors from annotations
        if (orderResult.hasErrors() || customerResult.hasErrors()) {
            return "orderForm";
        }

        try {
            @SuppressWarnings("unchecked")
            List<OrderItem> items = (List<OrderItem>) session.getAttribute("cart");
            orderService.saveOrder(customer, order, specialRequests, items);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "orderForm";
        }

        // Clear cart after save
        session.removeAttribute("cart");

        return "redirect:/order/success?orderId=" + order.getOrderId();
    }


    @GetMapping("/order/success")
    public String showSuccess(@RequestParam Integer orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "success";
    }

    @GetMapping("/order/details/{id}")
    public String showOrderDetails(@PathVariable Integer id, Model model) {
        Orders order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/orders"; // Or error page
        }
        Customer customer = orderService.getCustomerById(order.getCustomerId());
        List<SpecialRequest> specialRequests = orderService.getSpecialRequestsByOrderId(id);
        List<Object[]> orderItems = orderService.getOrderItemsWithDetailsByOrderId(id);
        BigDecimal totalAmount = orderService.calculateTotalAmount(id);

        model.addAttribute("order", order);
        model.addAttribute("customer", customer);
        model.addAttribute("specialRequests", specialRequests);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("totalAmount", totalAmount);

        return "orderDetails";
    }

    @GetMapping("/order/edit/{id}")
    public String showEditOrder(@PathVariable Integer id, Model model) {
        Orders order = orderService.getOrderById(id);
        if (order == null || !"Pending".equals(order.getStatus())) {
            return "redirect:/orders";
        }
        Customer customer = orderService.getCustomerById(order.getCustomerId());
        List<SpecialRequest> specialRequests = orderService.getSpecialRequestsByOrderId(id);
        List<Object[]> orderItems = orderService.getOrderItemsWithDetailsByOrderId(id);
        BigDecimal totalAmount = orderService.calculateTotalAmount(id);

        model.addAttribute("order", order);
        model.addAttribute("customer", customer);
        model.addAttribute("specialRequests", specialRequests);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("totalAmount", totalAmount);

        return "orderEdit";
    }

    @PostMapping("/order/update/{id}")
    public String updateOrder(@PathVariable Integer id,
                              @RequestParam String serviceType,
                              @RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String address,
                              @RequestParam String phoneNo,
                              @RequestParam String pickupDatetimeStr,
                              @RequestParam String deliveryDatetimeStr) {
        Orders order = orderService.getOrderById(id);
        Customer customer = orderService.getCustomerById(order.getCustomerId());

        order.setServiceType(serviceType);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        order.setPickupDatetime(LocalDateTime.parse(pickupDatetimeStr, formatter));
        order.setDeliveryDatetime(LocalDateTime.parse(deliveryDatetimeStr, formatter));

        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setPhoneNo(phoneNo);

        orderService.updateOrder(order, customer);

        return "redirect:/order/details/" + id;
    }

    @PostMapping("/order/update-requests/{id}")
    public String updateSpecialRequests(@PathVariable Integer id,
                                        @RequestParam List<String> specialRequests) {
        orderService.updateSpecialRequests(id, specialRequests);
        return "redirect:/order/edit/" + id;
    }

    @GetMapping("/order/delete-request/{id}")
    public String deleteSpecialRequest(@PathVariable Integer id,
                                       @RequestParam String name) {
        orderService.deleteSpecialRequest(id, name);
        return "redirect:/order/edit/" + id;
    }

    @GetMapping("/order/delete-item/{id}")
    public String deleteOrderItem(@PathVariable Integer id,
                                  @RequestParam Integer clothId) {
        orderService.deleteOrderItem(id, clothId);
        return "redirect:/order/edit/" + id;
    }

    @PostMapping("/order/update-item-quantity/{id}")
    public String updateOrderItemQuantity(@PathVariable Integer id,
                                          @RequestParam Integer clothId,
                                          @RequestParam Integer quantity) {
        orderService.updateOrderItemQuantity(id, clothId, quantity);
        return "redirect:/order/edit/" + id;
    }

    @PostMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        orderService.deleteOrder(id);
        redirectAttributes.addFlashAttribute("message", "Order successfully deleted");
        return "redirect:/orders";
    }
}