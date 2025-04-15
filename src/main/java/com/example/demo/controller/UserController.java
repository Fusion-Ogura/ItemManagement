package com.example.demo.controller;  

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.UserService;  // インポート追加 

@Controller  
public class UserController {  
    private final UserService userService;
    
    public UserController(UserService userService) {
    	this.userService = userService;
    }
    
    @GetMapping("/users")  
    public String getUsers(Model model) {  
        // サービス層からデータ取得  
        model.addAttribute("users", userService.findAll());  
        return "users";  
    }  
}
