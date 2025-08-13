package com.automated.trading.dao.UserDaoTests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.dao.UserDao.InsertUser;
import com.automated.trading.dao.UserDao.SelectUser;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.SelectUserDaoNoUsersFoundException;
import com.automated.trading.model.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SelectUserTests {

    @Autowired
    private InsertUser insertUser;

    @Autowired
    private SelectUser selectUser;

    private User insertedUser;

    @BeforeAll
    void setup() {
        String email = "selectTest@test.com";
        insertedUser = new User(email, "pw");
        insertUser.insertUser(insertedUser);
        insertedUser = selectUser.selectUserByEmail(email);
    }

    @Test
    public void selectAllReturnsListLargerThanZero() {
        List<User> users = selectUser.selectAllUsers();
        assertTrue(users.size() > 0);
    }

    @Test
    public void selectByIdReturnsCorrectUser() {
        User found = selectUser.selectUserById(insertedUser.getId());
        assertEquals(insertedUser.getEmail(), found.getEmail());
    }

    @Test
    public void selectByEmailReturnsCorrectUser() {
        User found = selectUser.selectUserByEmail(insertedUser.getEmail());
        assertEquals(insertedUser.getId(), found.getId());
    }

    @Test
    public void selectEmailByEmailReturnsCorrectEmail() {
        String foundEmail = selectUser.selectUserEmailByEmail(insertedUser.getEmail());
        assertEquals(insertedUser.getEmail(), foundEmail);
    }

    @Test
    public void selectEmailByEmailThrowsNoUsersFoundException() {
        String nullEmail = "ThisEmailDoesNotExist@nullemail.com";
        assertThrows(SelectUserDaoNoUsersFoundException.class, 
                    () -> selectUser.selectUserByEmail(nullEmail));
    }

    @AfterAll
    public static void cleanup(@Autowired DeleteUser deleteUser) {
        deleteUser.DeleteUserByEmail("selectTest@test.com");
    }
}
