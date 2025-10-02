package org.example.pickupanddelivery.repository;

import org.example.pickupanddelivery.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(String status);
    List<Order> findByPickupDate(LocalDate pickupDate);
    List<Order> findByDeliveryDate(LocalDate deliveryDate);
    List<Order> findByStatusNot(String status);
}