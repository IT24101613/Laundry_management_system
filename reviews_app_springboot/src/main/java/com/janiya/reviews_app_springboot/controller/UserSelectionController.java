package com.janiya.reviews_app_springboot.controller;

import com.janiya.reviews_app_springboot.model.User;
import com.janiya.reviews_app_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserSelectionController {

    @Autowired
    private UserService userService;

    @GetMapping("/select-user")
    public String selectUser(Model model) {
        List<User> users = userService.getAllUsers();
        System.out.println("Users being passed to view:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole());
        }
        model.addAttribute("users", users);
        return "select_user";
    }

    @PostMapping("/select-user")
    public String processUserSelection(@RequestParam("userId") Long userId, 
                                     @RequestParam("password") String password, 
                                     HttpSession session) {
        System.out.println("Processing user selection - UserId: " + userId + ", Password: " + password);
        
        User selectedUser = userService.getUserById(userId).orElse(null);
        System.out.println("Selected user: " + (selectedUser != null ? selectedUser.getUsername() : "null"));
        
        if (selectedUser != null) {
            System.out.println("User password: " + selectedUser.getPassword());
            System.out.println("Password match: " + selectedUser.getPassword().equals(password));
        }
        
        if (selectedUser != null && selectedUser.getPassword().equals(password)) {
            session.setAttribute("currentUser", selectedUser);
            return "redirect:/dashboard"; // Redirect to dashboard after user selection
        }
        return "redirect:/select-user?error=invalid_password";
    }
}