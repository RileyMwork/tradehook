package com.project.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.entity.User;
import com.project.exception.EmailIsTakenException;
import com.project.exception.EmailOrPasswordTooLongException;
import com.project.exception.EmailOrPasswordTooShortException;
import com.project.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> selectAllUsers() {
        return userRepository.findAll();
    }

    public User createNewUser(User newUser) {
        if (checkIfEmailOrPasswordIsTooShort(newUser)) {
            if (checkIfEmailOrPasswordIsTooLong(newUser)) {
                if (checkEmailIsUnique(newUser)) {
                    return userRepository.save(newUser);
                } else {
                    throw new EmailIsTakenException("Email is taken");
                }
            } else {
                throw new EmailOrPasswordTooLongException("Email or Password too Long");
            }
        } else {
            throw new EmailOrPasswordTooShortException("Email or Password too short!");
        }
    }

    private boolean checkEmailIsUnique(User newUser) {
        Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());
        return !existingUser.isPresent();  // Returns true if the email is unique
    }

    private boolean checkEmailAndPasswordLength(User newUser) {
        String newUserEmail = newUser.getEmail();
        String newUserPassword = newUser.getPassword();
        return newUserEmail.length() > 6 && newUserEmail.length() <= 24
            && newUserPassword.length() > 6 && newUserPassword.length() <= 24;
    }

    private boolean checkIfEmailOrPasswordIsTooShort(User newUser) {
        return newUser.getEmail().length() > 6 && newUser.getPassword().length() > 6;
    }

    private boolean checkIfEmailOrPasswordIsTooLong(User newUser) {
        return newUser.getEmail().length() <= 24 && newUser.getPassword().length() <= 24;
    }

    public User login(User loginUser) {
        Optional<User> userOpt = userRepository.findByEmail(loginUser.getEmail());
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(loginUser.getPassword())) {
            return userOpt.get();
        }
        return null;
    }

    public User selectUserById(int id){
        Optional<User> user = userRepository.selectUserById(id);
        return user.orElse(null); // Returns null if not found
    }

    public User selectUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null); // Returns null if not found
    }

    public void deleteUser(int id){
        Optional<User> user = userRepository.selectUserById(id);
        if (user.isPresent()) {
            userRepository.deleteUserById(id);
            System.out.println("User with id " + id + " deleted!");
        } else {
            System.out.println("No User Found");
        }
    }

    public User updateUserInfo(User user, String newEmail, String newPassword) {
        User updatedUser = new User(newEmail, newPassword);
        Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());
        
        if (existingUserOpt.isPresent()) {
            int id = existingUserOpt.get().getId();
            if (checkEmailAndPasswordLength(updatedUser)) {
                if (checkEmailIsUnique(updatedUser)) {
                    return userRepository.updateUserInfo(id, newEmail, newPassword).orElse(null);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public User updateUserTradehookApiKey(User user, String newTradehookApiKey) {
        Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());
        if (existingUserOpt.isPresent()) {
            int id = existingUserOpt.get().getId();
            return userRepository.updateUserTradehookApiKey(id, newTradehookApiKey).orElse(null);
        }
        return null;
    }

    public User updateUserAlpacaApiKeys(User user, String newAlpacaApiKey, String newAlpacaSecretKey) {
        Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());
        if (existingUserOpt.isPresent()) {
            int id = existingUserOpt.get().getId();
            return userRepository.updateUserAlpacaApiKeys(id, newAlpacaApiKey, newAlpacaSecretKey).orElse(null);
        }
        return null;
    }
}
