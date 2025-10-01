package com.laundry.laundry_management_system.service;

import com.laundry.laundry_management_system.dto.PaymentDto;
import com.laundry.laundry_management_system.model.Payment;
import com.laundry.laundry_management_system.model.PaymentMethod;
import com.laundry.laundry_management_system.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void processPayment(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setOrderId(dto.getOrderId());
        payment.setAmount(dto.getAmount());
        payment.setPaymentStatus("Pending");
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaymentDatetime(dto.getPaymentDatetime());

        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public void markAsCOD(int orderId) {
        paymentRepository.updateStatus(orderId, "Pending", "CashOnDelivery");
    }

    @Override
    public void markAsPaid(int orderId) {
        paymentRepository.updateStatus(orderId, "Paid", "CARD");
    }

    @Override
    public Payment getPaymentByOrderId(int orderId) {
        return paymentRepository.findByOrderId(orderId).orElse(null);
    }

    @Override
    public void markAsCancelled(int orderId) {
        paymentRepository.updateStatus(orderId, "Cancelled", "CashOnDelivery");
    }

    // ✅ Fixed: unwrap Optional safely
    public void recordPayment(int orderId, BigDecimal amount, String method, String status) {
        Optional<Payment> optionalPayment = paymentRepository.findLatestByOrderId(orderId);

        if (optionalPayment.isPresent()) {
            Payment existing = optionalPayment.get();
            existing.setPaymentMethod(PaymentMethod.valueOf(method));
            existing.setPaymentStatus(status);
            existing.setAmount(amount);
            existing.setPaymentDatetime(LocalDateTime.now());
            paymentRepository.updateStatus(existing.getOrderId(), status, method);
        } else {
            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setPaymentMethod(PaymentMethod.valueOf(method));
            payment.setPaymentStatus(status);
            payment.setAmount(amount);
            payment.setPaymentDatetime(LocalDateTime.now());
            paymentRepository.save(payment);
        }
    }
}
