package org.example.pickupanddelivery.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String specialInstructions;
    private LocalDate pickupDate;
    private LocalTime pickupTime;
    private LocalDate deliveryDate;
    private LocalTime deliveryTime;
    private String status;

    @ManyToOne
    private Driver pickupDriver;

    @ManyToOne
    private Driver deliveryDriver;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }
    public LocalDate getPickupDate() { return pickupDate; }
    public void setPickupDate(LocalDate pickupDate) { this.pickupDate = pickupDate; }
    public LocalTime getPickupTime() { return pickupTime; }
    public void setPickupTime(LocalTime pickupTime) { this.pickupTime = pickupTime; }
    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }
    public LocalTime getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(LocalTime deliveryTime) { this.deliveryTime = deliveryTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Driver getPickupDriver() { return pickupDriver; }
    public void setPickupDriver(Driver pickupDriver) { this.pickupDriver = pickupDriver; }
    public Driver getDeliveryDriver() { return deliveryDriver; }
    public void setDeliveryDriver(Driver deliveryDriver) { this.deliveryDriver = deliveryDriver; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}