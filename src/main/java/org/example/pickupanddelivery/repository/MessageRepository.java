package org.example.pickupanddelivery.repository;

import org.example.pickupanddelivery.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByDriverId(Long driverId);
    List<Message> findByDriverIdAndStatus(Long driverId, String status);
    List<Message> findByStatus(String status);
    List<Message> findByOrderId(Long orderId);
    List<Message> findByParentMessageIsNull(); // Get only parent messages (not replies)
    List<Message> findByParentMessageId(Long parentMessageId); // Get replies for a message

    // Custom query to get active parent messages (not resolved and no parent)
    @Query("SELECT m FROM Message m WHERE m.parentMessage IS NULL AND m.status != 'RESOLVED' ORDER BY m.createdAt DESC")
    List<Message> findActiveParentMessages();

    // Custom query to get active parent messages for a specific driver
    @Query("SELECT m FROM Message m WHERE m.driver.id = :driverId AND m.parentMessage IS NULL AND m.status != 'RESOLVED' ORDER BY m.createdAt DESC")
    List<Message> findActiveParentMessagesByDriver(@Param("driverId") Long driverId);
}