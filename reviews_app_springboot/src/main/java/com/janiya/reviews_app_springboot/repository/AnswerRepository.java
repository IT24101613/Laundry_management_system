package com.janiya.reviews_app_springboot.repository;

import com.janiya.reviews_app_springboot.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
