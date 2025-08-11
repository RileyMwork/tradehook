package com.automated.trading.dao.UserDao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.automated.trading.model.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InsertUserTests {

    @Autowired
    private InsertUser insertUser;

    @Autowired
    private SelectUser selectUser;

    @Test
    public void insertUserReturnsOneAndCorrectEmail() {
        String testEmail = "insertTest1@email.com";
        String testPassword = "password123";
        User userToInsert = new User(testEmail, testPassword);
        Integer result = insertUser.insertUser(userToInsert);
        User selected = selectUser.selectUserByEmail(testEmail);

        assertEquals(1, result);
        assertNotNull(selected);
        assertEquals(testEmail, selected.getEmail());
    }

    @Test
    public void insertUserReturnsOneAndCorrectTradehookApiKey() {
        String email = "tradehooktest1@test.com";
        String key = "api-key-123";
        User user = new User(email, "pw", key, null, null);
        Integer result = insertUser.insertUser(user);
        User selected = selectUser.selectUserByEmail(email);

        assertEquals(1, result);
        assertNotNull(selected);
        assertEquals(key, selected.getTradehookApiKey());
    }

    @AfterAll
    public static void cleanup(@Autowired DeleteUser deleteUser) {
        deleteUser.DeleteUserByEmail("insertTest1@email.com");
        deleteUser.DeleteUserByEmail("tradehooktest1@test.com");
    }
}

