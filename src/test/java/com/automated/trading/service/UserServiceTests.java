package com.automated.trading.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.exception.serviceexceptions.UserServiceNewUserInfoTooLongException;
import com.automated.trading.exception.serviceexceptions.UserServiceNoUserEmailFoundException;
import com.automated.trading.exception.serviceexceptions.UserServiceSelectedUserNotFoundException;
import com.automated.trading.exception.serviceexceptions.UserServiceUserAlreadyExistsException;
import com.automated.trading.model.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTests {

    @Autowired
    UserService userService;

    private String validEmail = "testingwithvalidemail@testemail.com";
    private String otherValidEmail = "testingwithothervalidemail@testemail.com";
    private String emailIsTaken = "bob@example.com";
    private String emailIsTooLong = "ThisEmailExactlySixtyFiveCharactersLongTest@verylongtestemail.com";
    private String emailToDelete1 = "thisemailwillgetdeleted@email.com";
    private String emailToDelete2 = "thisemailwillgetdeleted2@email.com";

    private String validPassword = "testingwithvalidpassword";
    private String invalidPassword = "testingwithinvalidpasswordwhichiswaytoolong";

    private User validUser;
    private User emailTakenUser;
    private User emailTooLongUser;
    private User passwordTooLongUser;
    private User userToDelete1;
    private User userToDelete2;

    @BeforeAll
    void setup() {
        validUser = new User(validEmail,validPassword,null,null,null);
        emailTakenUser = new User(emailIsTaken,validPassword,null,null,null);
        emailTooLongUser = new User(emailIsTooLong,validPassword,null,null,null);
        passwordTooLongUser = new User(100000000,otherValidEmail,invalidPassword,null,null,null);
        userToDelete1 = new User(emailToDelete1, validPassword, null, null, null);
        userToDelete2 = new User(emailToDelete2, validPassword, null, null, null);
    }

    @Test
    public void validCreateUserReturnsOneAndCorrectEmail() {;
        User validInsertedUser = userService.createNewUser(validUser);
        assertEquals(validUser.getEmail(), validInsertedUser.getEmail());
    }

    @Test
    public void createUserWithDuplicateEmailThrowsException() {
        assertThrows(UserServiceUserAlreadyExistsException.class, () -> userService.createNewUser(emailTakenUser));
    }

    @Test
    public void createUserWithTooLongEmailThrowsException() {
        assertThrows(UserServiceNewUserInfoTooLongException.class, () -> userService.createNewUser(emailTooLongUser));
    }

    @Test
    public void createUserWithTooLongPasswordThrowsException() {
        assertThrows(UserServiceNewUserInfoTooLongException.class, () -> userService.createNewUser(passwordTooLongUser));
    }

    @Test
    public void selectValidUserByEmailReturnsValidUser() {
        User createdUser = userService.selectUserByEmail(validUser);
        assertEquals(validUser.getEmail(), createdUser.getEmail());
        assertEquals(validUser.getPassword(), createdUser.getPassword());
    }

    @Test
    public void selectInvalidUserByEmailThrowsException() {
        assertThrows(UserServiceSelectedUserNotFoundException.class, () -> userService.selectUserByEmail(emailTooLongUser));
    }

    @Test
    public void selectValidUserByIdReturnsValidUser() {
        User createdUserWEmail = userService.selectUserByEmail(validUser);
        User createdUser = userService.selectUserById(createdUserWEmail);
        assertEquals(validUser.getEmail(), createdUser.getEmail());
        assertEquals(validUser.getPassword(), createdUser.getPassword());
    }

    @Test
    public void selectInvalidUserByIdThrowsException() {
        assertThrows(UserServiceSelectedUserNotFoundException.class, () -> userService.selectUserByEmail(emailTooLongUser));
    }

    @Test
    public void selectValidUserEmailByEmail() {
        User createdUser = userService.selectUserEmailByEmail(validUser);
        assertEquals(validUser.getEmail(), createdUser.getEmail());
        assertEquals(validUser.getPassword(), createdUser.getPassword());
    }

    @Test
    public void selectInvalidUserEmailByIdThrowsException() {
        assertThrows(UserServiceNoUserEmailFoundException.class, () -> userService.selectUserEmailByEmail(emailTooLongUser));
    }

    @Test
    public void updateNonExistentUserCatchesExceptionReturnsNull() {
        assertEquals(null,userService.updateUser(passwordTooLongUser));
    }

    @Test
    public void updateValidUserReturnsUpdatedUser() {
        User userToUpdate = userService.selectUserByEmail(validUser);
        User updatedUserInfo = new User(userToUpdate.getId(),"updatedvalidemail@testemail.com","updatedvalidpassword", "update", null, null);
        User postUpdateUser = userService.updateUser(updatedUserInfo);
        assertEquals(updatedUserInfo, postUpdateUser);
        
    }

    @Test
    public void deleteUserByIdReturnsOneAndDeletesUser() {
        userService.createNewUser(userToDelete2);
        User createdUser = userService.selectUserByEmail(userToDelete2);
        System.out.println(createdUser.getId());
        Integer deletedUser = userService.deleteUserById(createdUser);
        assertEquals(1, deletedUser);
        assertThrows(UserServiceSelectedUserNotFoundException.class, () -> userService.selectUserById(createdUser));
        
    }

    @Test
    public void deleteNonExistentUserByIdCatchesExceptionReturnsNull() {
        assertEquals(null,userService.deleteUserById(passwordTooLongUser));
    }

    @Test
    public void deleteUserByEmailReturnsOneAndDeletesUser() {
        userService.createNewUser(userToDelete1);
        assertEquals(1, userService.deleteUserByEmail(userToDelete1));
        assertThrows(UserServiceSelectedUserNotFoundException.class, () -> userService.selectUserByEmail(userToDelete1));   
    }

    @Test
    public void deleteNonExistentUserByEmailCatchesExceptionReturnsNull() {
        assertEquals(null,userService.deleteUserByEmail(emailTooLongUser));
    }

    @AfterAll
    public static void cleanup(@Autowired DeleteUser deleteUser) {
        deleteUser.deleteUserByEmail("updatedvalidemail@testemail.com");
    }
}

