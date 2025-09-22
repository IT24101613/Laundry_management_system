package com.pgno209.smartwash.model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private Integer orderId;
    private Integer clothId;
    private Integer quantity;

    // Getters and Setters
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getClothId() { return clothId; }
    public void setClothId(Integer clothId) { this.clothId = clothId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
