package com.project.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.project.entity.User;
import com.project.exception.EmailIsTakenException;
import com.project.exception.EmailOrPasswordTooLongException;
import com.project.exception.EmailOrPasswordTooShortException;
import com.project.service.APostService;
import com.project.service.TvPostService;
import com.project.service.UserService;

@Controller
public class TradehookController {
    
    private UserService userService;
    private TvPostService tvPostService;
    private APostService aPostService;

    public TradehookController(UserService userService, TvPostService tvPostService, APostService aPostService) {
        this.userService = userService;
        this.tvPostService = tvPostService;
        this.aPostService = aPostService;
    }

    @GetMapping("/register")
    public String registerForm() {
        // You can add data to the model if you want dynamic content in the view
        return "register"; // This corresponds to the 'register.html' template
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@RequestBody User user) {
        try {
            User newUser = userService.createNewUser(user);
        } catch (EmailOrPasswordTooShortException | EmailOrPasswordTooLongException | EmailIsTakenException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.status(200).body("New User Created!");

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> displayUsers() {
        List<User> users = userService.selectAllUsers();
        return ResponseEntity.status(200).body(users);
    }



}
