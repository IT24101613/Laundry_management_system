package com.laundry.laundry_management_system.model;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private int paymentId;
    private int orderId;
    private PaymentMethod paymentMethod;
    private String paymentStatus;
    private BigDecimal amount;
    private LocalDateTime paymentDatetime;

    public Payment() {
    }

    public Payment(int paymentId, int orderId, PaymentMethod paymentMethod, String paymentStatus, BigDecimal amount, LocalDateTime paymentDatetime) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.paymentDatetime = paymentDatetime;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDatetime() {
        return paymentDatetime;
    }

    public void setPaymentDatetime(LocalDateTime paymentDatetime) {
        this.paymentDatetime = paymentDatetime;
    }

    public void setPaymentMethod(@NotBlank(message = "Payment method is required") String paymentMethod) {
    }
}


