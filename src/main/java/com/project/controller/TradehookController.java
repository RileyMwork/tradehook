package com.project.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.fasterxml.jackson.databind.JsonNode;
import com.project.entity.TvPost;
import com.project.entity.User;
import com.project.exception.EmailIsTakenException;
import com.project.exception.EmailAndPasswordDoesNotMatch;
import com.project.exception.EmailOrPasswordTooLongException;
import com.project.exception.EmailOrPasswordTooShortException;
import com.project.service.APostService;
import com.project.service.TvPostService;
import com.project.service.UserService;
import jakarta.servlet.http.HttpSession;

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

    @GetMapping("/users")
    public ResponseEntity<List<User>> displayUsers() {
        List<User> users = userService.selectAllUsers();
        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/register")
    public String registerForm() {
        // You can add data to the model if you want dynamic content in the view
        return "register"; // This corresponds to the 'register.html' template
    }

    @PostMapping("/register")
    public String registerNewUser(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        User user = new User(email,password);

        try {
            userService.createNewUser(user);
            User loggedInUser = userService.selectUserByEmail(email);
            System.out.println(loggedInUser);
            session.setAttribute("user", loggedInUser);
        } catch (EmailOrPasswordTooShortException | EmailOrPasswordTooLongException | EmailIsTakenException e) {
            model.addAttribute("error", e.getMessage());  // Add error message to the model
            return "register";  // Return to the same registration page
        }

        return "redirect:/home";  // Redirect to the login page on successful registration
    }

    @GetMapping("/login")
    public String loginForm() {
        // You can add data to the model if you want dynamic content in the view
        return "login"; // This corresponds to the 'register.html' template
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        User user = new User(email,password);

        try {
            userService.login(user);  // Try to log the user in
            User loggedInUser = userService.selectUserByEmail(email);
            System.out.println(loggedInUser);
            session.setAttribute("user", loggedInUser);  // Store user in the session if login is successful
        } catch (EmailAndPasswordDoesNotMatch e) {
            model.addAttribute("error", e.getMessage());  // Add error message to the model
            return "login";  // Return to the same login page with error
        }

        return "redirect:/home";  // Redirect to the home page on successful login
    }

    @GetMapping("/home")
    public String homeForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");  // Get the user from session
        if (user != null) {
            model.addAttribute("user", user);  // Add user to model
            model.addAttribute("tradehookApiKey", user.getTradehookApiKey());
            model.addAttribute("alpacaApiKey", user.getAlpacaApiKey());
            model.addAttribute("alpacaSecretKey", user.getAlpacaSecretKey());
        } else {
            // Handle the case where user is not logged in
            return "redirect:/login";  // Redirect to login page if user is not logged in
        }
        return "home";  // Proceed to home page if user is logged in
    }
    

    @PostMapping("/updateTradehookApiKey")
    public String updateTradehookApiKey(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from session
        if (user != null) {
            // Call the service to update the user in the database
            user = userService.updateTradehookApiKey(user);  // Ensure this method updates the user in DB
            session.setAttribute("user", user);  // Update the session with the new user object
            model.addAttribute("message", "Tradehook API Key successfully updated!"); // Add success message
        } else {
            model.addAttribute("error", "User not found in session!");  // Handle case where user is not in session
        }
        return "redirect:/home";  // Redirect back to the home page
    }

    @PostMapping("/updateAlpacaApiKeys")
    public String updateAlpacaApiKeys(@RequestParam String alpacaApiKey, @RequestParam String alpacaSecretKey, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from session
        if (user != null) {
            // Call the service to update the user in the database
            user = userService.updateAlpacaApiKeys(user,alpacaApiKey,alpacaSecretKey); // Ensure this method updates the user in DB
            session.setAttribute("user", user);  // Update the session with the new user object
            model.addAttribute("message", "Tradehook API Key successfully updated!"); // Add success message
        } else {
            model.addAttribute("error", "User not found in session!");  // Handle case where user is not in session
        }
        return "redirect:/home";  // Redirect back to the home page
    }

    @GetMapping("/tvPosts")
    public ResponseEntity<List<TvPost>> displayTvPosts() {
        List<TvPost> tvPosts = tvPostService.selectAllTvPosts();
        return ResponseEntity.status(200).body(tvPosts);
    }
    

    @PostMapping("/receiveTvPost")
    public ResponseEntity<String> receiveTvPost(@RequestBody JsonNode json) {
        try {
            String tradehookApiKey = json.get("tradehookApiKey").asText();
            int userId = userService.selectUserByTradehookApiKey(tradehookApiKey).getId();
            System.out.println(json.toString());
            TvPost tvPost = new TvPost(userId,json.toString(),Timestamp.valueOf(LocalDateTime.now()));
            tvPostService.createNewTvPost(tvPost);
            return ResponseEntity.ok("TvPost successfully updated!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating TvPost: " + e.getMessage());
        }
    }

}