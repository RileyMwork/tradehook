package com.automated.trading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.dao.UserDao.InsertUser;
import com.automated.trading.dao.UserDao.SelectUser;
import com.automated.trading.dao.UserDao.UpdateUser;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.DeleteUserDaoNoUserToDelete;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.UpdateUserDaoNoUserFound;
import com.automated.trading.exception.serviceexceptions.UserServiceNewUserInfoTooLongException;
import com.automated.trading.exception.serviceexceptions.UserServiceNoUserEmailFoundException;
import com.automated.trading.exception.serviceexceptions.UserServicePasswordIncorrectException;
import com.automated.trading.exception.serviceexceptions.UserServiceSelectedUserNotFoundException;
import com.automated.trading.exception.serviceexceptions.UserServiceUserAlreadyExistsException;
import com.automated.trading.model.User;

import jakarta.servlet.http.HttpSession;

import java.util.UUID;

@Component
public class UserService {
    
    @Autowired
    private InsertUser insertUser;

    @Autowired
    private SelectUser selectUser;

    @Autowired
    private UpdateUser updateUser;

    @Autowired
    private DeleteUser deleteUser;
    
    @Autowired
private PasswordEncoder passwordEncoder;

    public User createNewUser(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        if (email.length() > 64) {
            throw new UserServiceNewUserInfoTooLongException("Email must be less than 64 characters");
        } 
        if (password.length() > 32 || password.length() < 8) {
            throw new UserServiceNewUserInfoTooLongException("Password must be between 8 and 32 characters");
        }
        if (selectUser.selectUserByEmail(email) != null) {
            throw new UserServiceUserAlreadyExistsException("User with this email already exists");
        }

        // ✅ Hash the password
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        Integer newRow = insertUser.insertUser(user);
        System.out.println("Rows Created: " + newRow);
        return selectUser.selectUserByEmail(email);
    }


    public User selectUserByEmail(User user) {
        User selectedUser = selectUser.selectUserByEmail(user.getEmail());
        if (selectedUser == null) {
            throw new UserServiceSelectedUserNotFoundException("Could not find User with email: " + user.getEmail());
        } else {
            return selectedUser;
        }
    }

    public User selectUserById(User user) {
        User selectedUser = selectUser.selectUserById(user.getId());
        if (selectedUser == null) {
            throw new UserServiceSelectedUserNotFoundException("Could not find User with ID: " + user.getId());
        } else {
            return selectedUser;
        }
    }

    public User selectUserByTradehookApiKey(String tradehookApiKey) {
        User selectedUser = selectUser.selectUserByTradehookApiKey(tradehookApiKey);
        if (selectedUser == null) {
            throw new UserServiceSelectedUserNotFoundException("Could not find User with key: " + tradehookApiKey);
        } else {
            return selectedUser;
        }
    }

    public User selectUserEmailByEmail(User user) {
        String email = selectUser.selectUserEmailByEmail(user.getEmail());
        if (email == null) {
            throw new UserServiceNoUserEmailFoundException("Could not find email: " + email);
        }
        User selectedUser = selectUser.selectUserByEmail(email);
        if (selectedUser == null) {
            throw new UserServiceSelectedUserNotFoundException("Could not find User with email: " + user.getEmail());
        } else {
            return selectedUser;
        }
    }

    public User updateAlpacaApiKeys(User user) {
        try {
            String email = user.getEmail();
            User existingUser = selectUser.selectUserByEmail(email);

            if (existingUser == null) {
                throw new UserServiceSelectedUserNotFoundException("User not found with email: " + email);
            }

            // Update the existing user's alpacaApiKey and alpacaSecretKey
            existingUser.setAlpacaApiKey(user.getAlpacaApiKey());
            existingUser.setAlpacaSecretKey(user.getAlpacaSecretKey());

            // Persist the updated user (if your `updateUser` method saves changes)
            updateUser.updateUserAlpacaKeys(existingUser);

            // Return the updated user
            return existingUser;
        } catch (UpdateUserDaoNoUserFound e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public User updateTradehookApiKey(User user) {
        try {
            String email = user.getEmail();
            User existingUser = selectUser.selectUserByEmail(email);

            if (existingUser == null) {
                throw new UserServiceSelectedUserNotFoundException("User not found with email: " + email);
            }
            UUID uuid = UUID.randomUUID();
            String randomTradehookKey = uuid.toString();
            user.setTradehookApiKey(randomTradehookKey);

            updateUser.updateUserTradehookKey(user);

            return user;
        } catch (UpdateUserDaoNoUserFound e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer deleteUserById(User user) {
        try {
            return deleteUser.deleteUserById(user.getId());
        } catch (DeleteUserDaoNoUserToDelete e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer deleteUserByEmail(User user) {
        try {
            return deleteUser.deleteUserByEmail(user.getEmail());
        } catch (DeleteUserDaoNoUserToDelete e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public User login(User user) {
        User loginUser = selectUser.selectUserByEmail(user.getEmail());
        if (loginUser == null) {
            throw new UserServiceNoUserEmailFoundException("Incorrect login information");
        }

        // ✅ Use hashed password check
        if (!passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            throw new UserServicePasswordIncorrectException("Incorrect login information");
        }

        return loginUser;
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Ends the session
        return "redirect:/tradehook/login"; // Redirect to login page
    }

}
