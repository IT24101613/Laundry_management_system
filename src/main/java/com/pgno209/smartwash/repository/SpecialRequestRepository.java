package com.pgno209.smartwash.repository;

import com.pgno209.smartwash.model.SpecialRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class SpecialRequestRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(SpecialRequest request) {
        final String sql = "INSERT INTO SpecialRequest (order_id, name) VALUES (?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, request.getOrderId());
            ps.setString(2, request.getName());
            return ps;
        });
    }
}