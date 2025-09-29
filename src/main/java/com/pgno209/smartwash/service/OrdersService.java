package com.pgno209.smartwash.service;

import com.pgno209.smartwash.model.*;
import com.pgno209.smartwash.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private SpecialRequestRepository specialRequestRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ClothesRepository clothesRepository;

    public void saveOrder(Customer customer, Orders order, String specialRequestsStr, List<OrderItem> items) {
        // Validate input data
        validateOrder(customer, order);

        // Save customer and get ID
        Integer customerId = customerRepository.save(customer);
        order.setCustomerId(customerId);

        // Save order and get ID
        Integer orderId = orderRepository.save(order);
        order.setOrderId(orderId);

        // Save special requests if any
        if (specialRequestsStr != null && !specialRequestsStr.isEmpty()) {
            List<String> requests = Arrays.asList(specialRequestsStr.split("\\r?\\n"));
            for (String req : requests) {
                if (!req.trim().isEmpty()) {
                    SpecialRequest specialRequest = new SpecialRequest();
                    specialRequest.setOrderId(orderId);
                    specialRequest.setName(req.trim());
                    specialRequestRepository.save(specialRequest);
                }
            }
        }

        // Save order items if any
        if (items != null) {
            for (OrderItem item : items) {
                item.setOrderId(orderId);
                orderItemRepository.save(item);
            }
        }
    }

    private void validateOrder(Customer customer, Orders order) {
        LocalDateTime now = LocalDateTime.now().withHour(6).withMinute(25).withSecond(0).withNano(0); // 06:25 AM +0530, Sep 28, 2025

        // Date validation (custom check not covered by annotations)
        if (order.getPickupDatetime().isBefore(now)) {
            throw new IllegalArgumentException("Pickup date must be greater than the current date (06:25 AM, Sep 28, 2025)");
        }
        if (order.getDeliveryDatetime().isBefore(now)) {
            throw new IllegalArgumentException("Delivery date must be greater than the current date (06:25 AM, Sep 28, 2025)");
        }
        if (order.getDeliveryDatetime().isBefore(order.getPickupDatetime())) {
            throw new IllegalArgumentException("Delivery date must be greater than pickup date");
        }
    }

    public List<Orders> getCurrentOrders() {
        return orderRepository.findCurrent();
    }

    public List<Orders> getCompletedOrders() {
        return orderRepository.findCompleted();
    }

    public Orders getOrderById(Integer orderId) {
        return orderRepository.findById(orderId);
    }

    public Customer getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    public List<SpecialRequest> getSpecialRequestsByOrderId(Integer orderId) {
        return specialRequestRepository.findByOrderId(orderId);
    }

    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public List<Object[]> getOrderItemsWithDetailsByOrderId(Integer orderId) {
        return orderItemRepository.findItemsWithClothDetailsByOrderId(orderId);
    }

    public BigDecimal calculateTotalAmount(Integer orderId) {
        List<Object[]> items = getOrderItemsWithDetailsByOrderId(orderId);
        BigDecimal total = BigDecimal.ZERO;
        for (Object[] item : items) {
            int quantity = (int) item[0];
            BigDecimal unitPrice = (BigDecimal) item[2];
            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
            total = total.add(itemTotal);
        }
        return total;
    }

    public void updateOrder(Orders order, Customer customer) {
        customerRepository.update(customer);
        orderRepository.update(order);
    }

    public void updateSpecialRequests(Integer orderId, List<String> requests) {
        specialRequestRepository.deleteByOrderId(orderId);
        for (String req : requests) {
            if (!req.trim().isEmpty()) {
                SpecialRequest request = new SpecialRequest();
                request.setOrderId(orderId);
                request.setName(req.trim());
                specialRequestRepository.insert(request);
            }
        }
    }

    public void deleteSpecialRequest(Integer orderId, String name) {
        specialRequestRepository.deleteByOrderIdAndName(orderId, name);
    }

    public void deleteOrderItem(Integer orderId, Integer clothId) {
        orderItemRepository.deleteByOrderIdAndClothId(orderId, clothId);
    }

    public void updateOrderItemQuantity(Integer orderId, Integer clothId, Integer quantity) {
        if (quantity > 0) {
            orderItemRepository.updateQuantity(orderId, clothId, quantity);
        } else {
            deleteOrderItem(orderId, clothId);
        }
    }

    public void deleteOrder(Integer orderId) {
        orderItemRepository.deleteByOrderId(orderId);
        specialRequestRepository.deleteByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }
}

