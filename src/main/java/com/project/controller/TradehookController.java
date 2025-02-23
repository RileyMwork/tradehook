package com.project.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.project.entity.User;
import com.project.service.APostService;
import com.project.service.TvPostService;
import com.project.service.UserService;

@RestController
public class TradehookController {
    
    private UserService userService;
    private TvPostService tvPostService;
    private APostService aPostService;

    public TradehookController(UserService userService, TvPostService tvPostService, APostService aPostService) {
        this.userService = userService;
        this.tvPostService = tvPostService;
        this.aPostService = aPostService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        User newUser = userService.createNewUser(user);
        return ResponseEntity.status(200).body(newUser);

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> displayUsers() {
        List<User> users = userService.selectAllUsers();
        return ResponseEntity.status(200).body(users);
    }



}
