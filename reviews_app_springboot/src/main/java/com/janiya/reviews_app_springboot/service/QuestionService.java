package com.janiya.reviews_app_springboot.service;

import com.janiya.reviews_app_springboot.model.Question;
import com.janiya.reviews_app_springboot.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    
    @Autowired
    private QuestionRepository questionRepository;
    
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }
    
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }
    
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
    
    public Question resolveQuestion(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            Question resolvedQuestion = question.get();
            resolvedQuestion.setResolved(true);
            return questionRepository.save(resolvedQuestion);
        }
        return null;
    }
}
