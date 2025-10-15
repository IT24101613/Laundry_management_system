package com.janiya.reviews_app_springboot.controller;

import com.janiya.reviews_app_springboot.model.Complaint;
import com.janiya.reviews_app_springboot.model.User;
import com.janiya.reviews_app_springboot.service.ComplaintService;
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
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public String getAllComplaints(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/select-user";
        }
        model.addAttribute("complaints", complaintService.getAllComplaints());
        return "complaints"; // JSP page name
    }

    @GetMapping("/new")
    public String showComplaintForm(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"customer".equals(currentUser.getRole())) {
            return "redirect:/select-user";
        }
        model.addAttribute("complaint", new Complaint());
        return "complaint_form"; // JSP page name
    }

    @PostMapping
    public String saveComplaint(@ModelAttribute("complaint") Complaint complaint, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"customer".equals(currentUser.getRole())) {
            return "redirect:/select-user";
        }
        complaint.setCustomerName(currentUser.getUsername());
        complaintService.saveComplaint(complaint);
        return "redirect:/complaints";
    }

    @GetMapping("/resolve/{id}")
    public String resolveComplaint(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            return "redirect:/select-user";
        }
        complaintService.resolveComplaint(id);
        return "redirect:/complaints";
    }
}