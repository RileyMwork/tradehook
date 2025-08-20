package com.automated.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.automated.trading.model.User;
import com.automated.trading.service.UserService;

@RestController
@RequestMapping("/api/user")
public class MainController {

    @Autowired
    private UserService userService;

    // POST /api/user/create
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }

    // POST /api/user/login
    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        return userService.selectUserByEmail(user);  // This assumes you match by email/password
    }

    // PUT /api/user/update
    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    // DELETE /api/user/delete-by-email
    @DeleteMapping("/delete-by-email")
    public Integer deleteUserByEmail(@RequestBody User user) {
        return userService.deleteUserByEmail(user);
    }
}
