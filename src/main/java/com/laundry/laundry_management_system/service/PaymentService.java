package com.laundry.laundry_management_system.service;

import com.laundry.laundry_management_system.dto.PaymentDto;
import com.laundry.laundry_management_system.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    void processPayment(PaymentDto dto);
    List<Payment> getAllPayments();
    void markAsCOD(int orderId);
    void markAsPaid(int orderId);
    Payment getPaymentByOrderId(int orderId);
    void markAsCancelled(int orderId);

    void recordPayment(int orderId, BigDecimal amount, String card, String paid);
}



