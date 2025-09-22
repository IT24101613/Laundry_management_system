package com.pgno209.smartwash.service;

import com.pgno209.smartwash.model.Customer;
import com.pgno209.smartwash.model.OrderItem;
import com.pgno209.smartwash.model.Orders;
import com.pgno209.smartwash.model.SpecialRequest;
import com.pgno209.smartwash.repository.CustomerRepository;
import com.pgno209.smartwash.repository.OrderItemRepository;
import com.pgno209.smartwash.repository.OrdersRepository;
import com.pgno209.smartwash.repository.SpecialRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    public void saveOrder(Customer customer, Orders order, String specialRequestsStr, List<OrderItem> items) {
        // Save customer and get ID
        Integer customerId = customerRepository.save(customer);
        order.setCustomerId(customerId);

        // Save order and get ID
        Integer orderId = orderRepository.save(order);

        // set the generated orderId back to the object
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

    public List<Orders> getPendingOrders() {
        return orderRepository.findPending();
    }

    public List<Orders> getCompletedOrders() {
        return orderRepository.findCompleted();
    }
}