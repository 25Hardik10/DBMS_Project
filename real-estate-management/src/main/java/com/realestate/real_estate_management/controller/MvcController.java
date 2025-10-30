package com.realestate.real_estate_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class MvcController {

    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    
    @GetMapping("/details")
    public String details() {
        return "details";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @GetMapping("/register") // <-- New Route for Registration
    public String register() {
        return "register";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile"; 
    }
    @GetMapping("/admin")
    public String adminPanel() {
        return "admin"; // This will serve admin.html
    }
}