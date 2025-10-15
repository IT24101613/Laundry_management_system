package com.janiya.reviews_app_springboot.service;

import com.janiya.reviews_app_springboot.model.Answer;
import com.janiya.reviews_app_springboot.model.Question;
import com.janiya.reviews_app_springboot.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerService {
    
    @Autowired
    private AnswerRepository answerRepository;
    
    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return answerRepository.findAll().stream()
                .filter(answer -> answer.getQuestion().getId().equals(questionId))
                .collect(Collectors.toList());
    }
    
    public Optional<Answer> getAnswerById(Long id) {
        return answerRepository.findById(id);
    }
    
    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }
    
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}
