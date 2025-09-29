package com.pgno209.smartwash.model;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class Orders {
    private Integer orderId;
    private Integer customerId;
    @NotNull(message = "Service Type is required")
    private String serviceType;
    @NotNull(message = "Pickup Date and Time is required")
    private LocalDateTime pickupDatetime;
    @NotNull(message = "Delivery Date and Time is required")
    private LocalDateTime deliveryDatetime;
    private LocalDateTime orderTime;
    private String status;

    // For form handling, not persisted directly
    private List<String> specialRequests;

    // Getters and Setters
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public LocalDateTime getPickupDatetime() { return pickupDatetime; }
    public void setPickupDatetime(LocalDateTime pickupDatetime) { this.pickupDatetime = pickupDatetime; }
    public LocalDateTime getDeliveryDatetime() { return deliveryDatetime; }
    public void setDeliveryDatetime(LocalDateTime deliveryDatetime) { this.deliveryDatetime = deliveryDatetime; }
    public LocalDateTime getOrderTime() { return orderTime; }
    public void setOrderTime(LocalDateTime orderTime) { this.orderTime = orderTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<String> getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(List<String> specialRequests) { this.specialRequests = specialRequests; }
}