package com.jey.blogapp.controller;

import com.jey.blogapp.entity.User;
import com.jey.blogapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showMyLoginPage(HttpServletRequest request) {
        if(request.getParameter("logout") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/register")
    public String showMyRegisterPage(HttpServletRequest request) {
        return "register";
    }

    @PostMapping("/process")
    public String register(HttpServletRequest request) {
        if(!request.getParameter("password").equals(request.getParameter("password2"))) {
            return "redirect:/register?error";
        }

        String name = request.getParameter("name");
        String password = "{noop}"+request.getParameter("password");
        String email = request.getParameter("email");

        User user = new User(name, email, password);
        userService.save(user);

        return "redirect:/login";
    }
}
