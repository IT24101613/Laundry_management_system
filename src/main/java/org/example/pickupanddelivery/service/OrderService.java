package org.example.pickupanddelivery.service;

import org.example.pickupanddelivery.model.Driver;
import org.example.pickupanddelivery.model.Order;
import org.example.pickupanddelivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DriverService driverService;

    public Order saveOrder(Order order) {
        if (!isTimeSlotAvailable(order.getPickupDate(), order.getPickupTime(), "pickup")) {
            throw new IllegalArgumentException("Selected pickup time slot is not available. Please choose another time.");
        }
        if (!isTimeSlotAvailable(order.getDeliveryDate(), order.getDeliveryTime(), "delivery")) {
            throw new IllegalArgumentException("Selected delivery time slot is not available. Please choose another time.");
        }

        if (order.getPickupDate() != null && order.getDeliveryDate() != null) {
            LocalDateTime pickupDateTime = order.getPickupDate().atTime(order.getPickupTime() != null ? order.getPickupTime() : LocalTime.of(0, 0));
            LocalDateTime deliveryDateTime = order.getDeliveryDate().atTime(order.getDeliveryTime() != null ? order.getDeliveryTime() : LocalTime.of(0, 0));

            if (pickupDateTime.isAfter(deliveryDateTime)) {
                throw new IllegalArgumentException("Pickup date/time must be before delivery date/time");
            }
        }

        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    public List<Order> getOrdersForDriver(Long driverId) {
        return orderRepository.findAll().stream()
                .filter(order -> (order.getPickupDriver() != null && order.getPickupDriver().getId().equals(driverId)) ||
                        (order.getDeliveryDriver() != null && order.getDeliveryDriver().getId().equals(driverId)))
                .collect(Collectors.toList());
    }

    public boolean isTimeSlotAvailable(LocalDate date, LocalTime time, String type) {
        List<Order> orders;
        if ("pickup".equals(type)) {
            orders = orderRepository.findByPickupDate(date);
        } else {
            orders = orderRepository.findByDeliveryDate(date);
        }
        long conflicts = orders.stream()
                .filter(order -> ("pickup".equals(type) ? order.getPickupTime() : order.getDeliveryTime()).equals(time))
                .count();
        long maxBookings = driverService.getDriverCount();
        return conflicts < maxBookings;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getTodayPickups() {
        return orderRepository.findByPickupDate(LocalDate.now());
    }

    public List<Order> getTodayDeliveries() {
        return orderRepository.findByDeliveryDate(LocalDate.now());
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order updateStatus(Long id, String newStatus) {
        Order order = getOrderById(id);
        if (order != null) {
            order.setStatus(newStatus);
            return orderRepository.save(order);
        }
        return null;
    }

    public Order assignPickupDriver(Long orderId, Long driverId) {
        Order order = getOrderById(orderId);
        Driver driver = driverService.getDriverById(driverId);
        if (order != null && driver != null && "PENDING".equals(order.getStatus())) {
            order.setPickupDriver(driver);
            order.setStatus("PICKUP_ASSIGNED");
            return orderRepository.save(order);
        }
        return null;
    }

    public Order assignDeliveryDriver(Long orderId, Long driverId) {
        Order order = getOrderById(orderId);
        Driver driver = driverService.getDriverById(driverId);
        if (order != null && driver != null && "READY".equals(order.getStatus())) {
            order.setDeliveryDriver(driver);
            order.setStatus("DELIVERY_ASSIGNED");
            return orderRepository.save(order);
        }
        return null;
    }

    public List<Order> getPendingOrders() {
        return orderRepository.findByStatusNot("DELIVERED");
    }

    public List<LocalTime> getAvailableTimes(LocalDate date, String type) {
        List<LocalTime> allSlots = List.of(
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0)
        );
        List<Order> orders;
        if ("pickup".equals(type)) {
            orders = orderRepository.findByPickupDate(date);
        } else {
            orders = orderRepository.findByDeliveryDate(date);
        }
        Map<LocalTime, Long> counts = orders.stream()
                .collect(Collectors.groupingBy(o -> "pickup".equals(type) ? o.getPickupTime() : o.getDeliveryTime(), Collectors.counting()));
        long maxBookings = driverService.getDriverCount();
        return allSlots.stream()
                .filter(t -> counts.getOrDefault(t, 0L) < maxBookings)
                .collect(Collectors.toList());
    }
}