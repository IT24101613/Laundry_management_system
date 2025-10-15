package com.janiya.reviews_app_springboot.controller;

import com.janiya.reviews_app_springboot.model.Answer;
import com.janiya.reviews_app_springboot.model.Question;
import com.janiya.reviews_app_springboot.model.User;
import com.janiya.reviews_app_springboot.service.AnswerService;
import com.janiya.reviews_app_springboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private AnswerService answerService;
    
    @GetMapping
    public String getAllQuestions(Model model, HttpSession session) {
        System.out.println("Getting all questions");
        
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            System.out.println("No current user found, redirecting to select-user");
            return "redirect:/select-user";
        }
        
        System.out.println("Current user: " + currentUser.getUsername());
        List<Question> questions = questionService.getAllQuestions();
        System.out.println("Found " + questions.size() + " questions");
        model.addAttribute("questions", questions);
        return "questions";
    }
    
    @GetMapping("/new")
    public String showQuestionForm(Model model, HttpSession session) {
        System.out.println("Showing question form");
        
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            System.out.println("No current user found, redirecting to select-user");
            return "redirect:/select-user";
        }
        
        System.out.println("Current user: " + currentUser.getUsername());
        model.addAttribute("question", new Question());
        return "question_form";
    }
    
    @PostMapping
    public String saveQuestion(@ModelAttribute("question") Question question, HttpSession session) {
        System.out.println("Saving question: " + question.getQuestionText());
        
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            System.out.println("No current user found, redirecting to select-user");
            return "redirect:/select-user";
        }
        
        System.out.println("Current user: " + currentUser.getUsername());
        question.setCustomerName(currentUser.getUsername());
        questionService.saveQuestion(question);
        System.out.println("Question saved successfully");
        return "redirect:/questions";
    }
    
    @GetMapping("/{id}")
    public String getQuestionDetails(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/select-user";
        }
        
        Question question = questionService.getQuestionById(id).orElse(null);
        if (question == null) {
            return "redirect:/questions";
        }
        
        List<Answer> answers = answerService.getAnswersByQuestionId(id);
        
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        model.addAttribute("newAnswer", new Answer());
        return "question_details";
    }
    
    @PostMapping("/{id}/answers")
    public String addAnswer(@PathVariable Long id, @ModelAttribute("newAnswer") Answer answer, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/select-user";
        }
        
        Question question = questionService.getQuestionById(id).orElse(null);
        if (question == null) {
            return "redirect:/questions";
        }
        
        answer.setAuthorName(currentUser.getUsername());
        answer.setAuthorRole(currentUser.getRole());
        answer.setQuestion(question);
        
        answerService.saveAnswer(answer);
        return "redirect:/questions/" + id;
    }
    
    @GetMapping("/{id}/resolve")
    public String resolveQuestion(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.getRole().equals("admin")) {
            return "redirect:/questions";
        }
        
        questionService.resolveQuestion(id);
        return "redirect:/questions/" + id;
    }
    
    @GetMapping("/{id}/delete")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.getRole().equals("admin")) {
            return "redirect:/questions";
        }
        
        questionService.deleteQuestion(id);
        return "redirect:/questions";
    }
}
