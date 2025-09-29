package com.pgno209.smartwash.repository;

import com.pgno209.smartwash.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrdersRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer save(Orders order) {
        final String sql = "INSERT INTO Orders (customer_id, service_type, pickup_datetime, delivery_datetime, order_time, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getCustomerId());
            ps.setString(2, order.getServiceType());
            ps.setTimestamp(3, Timestamp.valueOf(order.getPickupDatetime()));
            ps.setTimestamp(4, Timestamp.valueOf(order.getDeliveryDatetime()));
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now())); // Set current time
            ps.setString(6, "Pending"); // Initial status
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Orders findById(Integer orderId) {
        String sql = "SELECT * FROM Orders WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, new OrderRowMapper(), orderId);
    }

    public List<Orders> findCurrent() {
        String sql = "SELECT * FROM Orders WHERE status = 'Pending' or status = 'Paid'";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    public List<Orders> findCompleted() {
        String sql = "SELECT * FROM Orders WHERE status = 'Completed'";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    private static class OrderRowMapper implements RowMapper<Orders> {
        @Override
        public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
            Orders order = new Orders();
            order.setOrderId(rs.getInt("order_id"));
            order.setCustomerId(rs.getInt("customer_id"));
            order.setServiceType(rs.getString("service_type"));
            order.setPickupDatetime(rs.getTimestamp("pickup_datetime").toLocalDateTime());
            order.setDeliveryDatetime(rs.getTimestamp("delivery_datetime").toLocalDateTime());
            order.setOrderTime(rs.getTimestamp("order_time").toLocalDateTime());
            order.setStatus(rs.getString("status"));
            return order;
        }
    }

    public void update(Orders order) {
        String sql = "UPDATE Orders SET service_type = ?, pickup_datetime = ?, delivery_datetime = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, order.getServiceType(), Timestamp.valueOf(order.getPickupDatetime()), Timestamp.valueOf(order.getDeliveryDatetime()), order.getOrderId());
    }

    public void deleteById(Integer orderId) {
        String sql = "DELETE FROM Orders WHERE order_id = ?";
        jdbcTemplate.update(sql, orderId);
    }
}