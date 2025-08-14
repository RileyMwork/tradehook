package com.automated.trading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.dao.UserDao.InsertUser;
import com.automated.trading.dao.UserDao.SelectUser;
import com.automated.trading.dao.UserDao.UpdateUser;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.DeleteUserDaoNoUserToDelete;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.UpdateUserDaoNoUserFound;
import com.automated.trading.exception.serviceexceptions.UserServiceNewUserInfoTooLongException;
import com.automated.trading.exception.serviceexceptions.UserServiceNoUserEmailFoundException;
import com.automated.trading.exception.serviceexceptions.UserServiceSelectedUserNotFoundException;
import com.automated.trading.exception.serviceexceptions.UserServiceUserAlreadyExistsException;
import com.automated.trading.model.User;

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
        else {
            Integer newRow = insertUser.insertUser(user);
            System.out.println("Rows Created: " + newRow);
            User newUser = selectUser.selectUserByEmail(email);
            return newUser;
        }
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

    public User updateUser(User user) {
        try {
            updateUser.updateUser(user);
            User updatedUser = selectUser.selectUserByEmail(user.getEmail());
            return updatedUser;
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

}
