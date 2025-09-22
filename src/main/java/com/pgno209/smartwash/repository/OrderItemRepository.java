package com.pgno209.smartwash.repository;

import com.pgno209.smartwash.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(OrderItem item) {
        String sql = "INSERT INTO OrderItem (order_id, cloth_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, item.getOrderId(), item.getClothId(), item.getQuantity());
    }
}