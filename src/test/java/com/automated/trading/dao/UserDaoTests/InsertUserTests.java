package com.automated.trading.dao.UserDaoTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.dao.UserDao.InsertUser;
import com.automated.trading.dao.UserDao.SelectUser;
import com.automated.trading.model.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InsertUserTests {

    @Autowired
    private InsertUser insertUser;

    @Autowired
    private SelectUser selectUser;

    private String testEmail;
    private String testPassword;
    private String testTradehookApiKey;
    private String testAlpacaApiKey;
    private String testAlpacaSecretKey;
    private User userToInsert;
    private User selected;
    private Integer result;

    @BeforeAll
    void setup() {
        testEmail = "insertTest1@email.com";
        testPassword = "password123";
        testTradehookApiKey = "api-key-123";
        testAlpacaApiKey = "alpaca-key-123";
        testAlpacaSecretKey = "alpaca-secret-key-123";
        userToInsert = new User(testEmail, testPassword, testTradehookApiKey, testAlpacaApiKey, testAlpacaSecretKey);
        result = insertUser.insertUser(userToInsert);
        selected = selectUser.selectUserByEmail(testEmail);
    }

    @Test
    public void insertUserReturnsOneAndCorrectEmail() {
        assertEquals(1, result);
        assertNotNull(selected);
        assertEquals(testEmail, selected.getEmail());
    }

    @Test
    public void insertUserReturnsOneAndCorrectTradehookApiKey() {
        assertEquals(1, result);
        assertNotNull(selected);
        assertEquals(testTradehookApiKey, selected.getTradehookApiKey());
    }

    @Test
    public void insertUserThrowsNullExceptionWhenAttributeIsNull() {
        assertThrows(NullPointerException.class, () -> insertUser.insertUser(null));
    }

    @AfterAll
    public static void cleanup(@Autowired DeleteUser deleteUser) {
        deleteUser.DeleteUserByEmail("insertTest1@email.com");
    }
}

