package com.janiya.reviews_app_springboot.controller;

import com.janiya.reviews_app_springboot.model.Review;
import com.janiya.reviews_app_springboot.model.User;
import com.janiya.reviews_app_springboot.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String getAllReviews(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/select-user";
        }
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "reviews"; // JSP page name
    }

    @GetMapping("/new")
    public String showReviewForm(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/select-user";
        }
        model.addAttribute("review", new Review());
        return "review_form"; // JSP page name
    }

    @PostMapping
    public String saveReview(@ModelAttribute("review") Review review, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/select-user";
        }
        review.setCustomerName(currentUser.getUsername()); // Set customer name from logged-in user
        reviewService.saveReview(review);
        return "redirect:/reviews";
    }

    @GetMapping("/edit/{id}")
    public String showEditReviewForm(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/select-user";
        }
        Review review = reviewService.getReviewById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + id));
        model.addAttribute("review", review);
        return "review_form";
    }

    @PostMapping("/edit/{id}")
    public String updateReview(@PathVariable Long id, @ModelAttribute("review") Review review, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/select-user";
        }
        review.setId(id);
        review.setCustomerName(currentUser.getUsername()); // Ensure customer name is updated
        reviewService.saveReview(review);
        return "redirect:/reviews";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/select-user";
        }
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}