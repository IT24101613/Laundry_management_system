package com.janiya.reviews_app_springboot.repository;

import com.janiya.reviews_app_springboot.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}