package com.automated.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.automated.trading.exception.serviceexceptions.UserServiceNewUserInfoTooLongException;
import com.automated.trading.exception.serviceexceptions.UserServiceNoUserEmailFoundException;
import com.automated.trading.exception.serviceexceptions.UserServicePasswordIncorrectException;
import com.automated.trading.exception.serviceexceptions.UserServiceUserAlreadyExistsException;
import com.automated.trading.model.User;
import com.automated.trading.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/tradehook/user")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /api/user/create
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user,HttpSession session) {
        try {
            User createdUser = userService.createNewUser(user);
            session.setAttribute("email", createdUser.getEmail());
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);  // Return user with 201 Created status
        } catch (UserServiceNewUserInfoTooLongException | UserServiceUserAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>((e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
        try {
            User loginUser = userService.login(user);
            session.setAttribute("email", loginUser.getEmail());
            return new ResponseEntity<>(loginUser, HttpStatus.OK);
        } catch (UserServiceNoUserEmailFoundException | UserServicePasswordIncorrectException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/alpaca-keys/update")
    public ResponseEntity<?> updateUserAlpacaKeys(@RequestBody User user, HttpSession session) {
        String email = (String) session.getAttribute("email");

        // Check if user is authenticated
        if (email == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        // Set the email field in the user object before passing it to the service method
        user.setEmail(email);

        // Call the service method to update the alpaca keys
        User updatedUser = userService.updateAlpacaApiKeys(user);

        // Return updated user information in response
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update Alpaca keys", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/tradehook-key/update")
    public ResponseEntity<?> updateUserTradehookKey(@RequestBody User user, HttpSession session) {
        String email = (String) session.getAttribute("email");
    
        // Check if user is authenticated
        if (email == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    
        // Set the email field in the user object before passing it to the service method
        user.setEmail(email);
    
        // Call the service method to update the alpaca keys
        User updatedUser = userService.updateTradehookApiKey(user);
        
        // Return updated user information in response
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update Tradehook key", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        User currentUser = new User(email);
        userService.deleteUserByEmail(currentUser);
        return new ResponseEntity<>("User Deleted",HttpStatus.OK);
    }
}


