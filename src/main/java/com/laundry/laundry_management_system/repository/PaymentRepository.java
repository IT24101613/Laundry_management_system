package com.laundry.laundry_management_system.repository;

import com.laundry.laundry_management_system.model.Payment;
import com.laundry.laundry_management_system.model.PaymentMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ✅ Save a new payment record
    public int save(Payment payment) {
        String sql = "INSERT INTO payment (order_id, payment_method, payment_status, amount, payment_datetime) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                payment.getOrderId(),
                payment.getPaymentMethod().name(),
                payment.getPaymentStatus(),
                payment.getAmount(),
                Timestamp.valueOf(payment.getPaymentDatetime())
        );
    }

    // ✅ Fetch all payments
    public List<Payment> findAll() {
        String sql = "SELECT * FROM payment";
        return jdbcTemplate.query(sql, mapRow());
    }

    // ✅ Fetch payment by order ID (first match)
    public Optional<Payment> findByOrderId(int orderId) {
        String sql = "SELECT * FROM payment WHERE order_id = ?";
        List<Payment> result = jdbcTemplate.query(sql, mapRow(), orderId);
        return result.stream().findFirst();
    }

    // ✅ Fetch latest payment by order ID (most recent)
    public Optional<Payment> findLatestByOrderId(int orderId) {
        String sql = "SELECT * FROM payment WHERE order_id = ? ORDER BY payment_datetime DESC LIMIT 1";
        List<Payment> result = jdbcTemplate.query(sql, mapRow(), orderId);
        return result.stream().findFirst();
    }

    // ✅ Update payment status, method, and timestamp
    public int updateStatus(int orderId, String status, String method) {
        String sql = "UPDATE payment SET payment_status = ?, payment_method = ?, payment_datetime = ? WHERE order_id = ?";
        return jdbcTemplate.update(sql, status, method, Timestamp.valueOf(LocalDateTime.now()), orderId);
    }

    // ✅ RowMapper for converting DB rows to Payment objects
    private RowMapper<Payment> mapRow() {
        return (ResultSet rs, int rowNum) -> {
            Payment p = new Payment();
            p.setPaymentId(rs.getInt("payment_id"));
            p.setOrderId(rs.getInt("order_id"));
            p.setPaymentMethod(PaymentMethod.valueOf(rs.getString("payment_method")));
            p.setPaymentStatus(rs.getString("payment_status"));
            p.setAmount(rs.getBigDecimal("amount"));
            p.setPaymentDatetime(rs.getTimestamp("payment_datetime").toLocalDateTime());
            return p;
        };
    }
}
