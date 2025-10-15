package com.janiya.reviews_app_springboot.controller;

import com.janiya.reviews_app_springboot.model.User;
import com.janiya.reviews_app_springboot.service.ReviewService;
import com.janiya.reviews_app_springboot.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            return "redirect:/select-user";
        }
        return "admin/dashboard";
    }

    @GetMapping("/reviews")
    public String adminReviews(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            return "redirect:/select-user";
        }
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "admin/reviews";
    }

    @GetMapping("/complaints")
    public String adminComplaints(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            return "redirect:/select-user";
        }
        model.addAttribute("complaints", complaintService.getAllComplaints());
        return "admin/complaints";
    }

    @GetMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            return "redirect:/select-user";
        }
        reviewService.deleteReview(id);
        return "redirect:/admin/reviews";
    }

    @GetMapping("/complaints/resolve/{id}")
    public String resolveComplaint(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            return "redirect:/select-user";
        }
        complaintService.resolveComplaint(id);
        return "redirect:/admin/complaints";
    }
}
