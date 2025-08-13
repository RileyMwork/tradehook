package com.automated.trading.dao.UserDaoTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.dao.UserDao.InsertUser;
import com.automated.trading.dao.UserDao.SelectUser;
import com.automated.trading.dao.UserDao.UpdateUser;
import com.automated.trading.model.User;

@SpringBootTest
public class UpdateUserTests {

    @Autowired
    private InsertUser insertUser;

    @Autowired
    private SelectUser selectUser;

    @Autowired
    private UpdateUser updateUser;

    @Test
    public void testUpdateUserReturnsOneAndUpdatesFields() {
        String originalEmail = "updateTest@email.com";
        String originalPassword = "originalPassword";
        User userToInsert = new User(originalEmail, originalPassword);
        Integer inserted = insertUser.insertUser(userToInsert);
        assertEquals(1, inserted);

        User user = selectUser.selectUserByEmail(originalEmail);
        assertNotNull(user);

        user.setEmail("updatedEmail@email.com");
        user.setPassword("updatedPassword");
        user.setTradehookApiKey("updatedTradehookApiKey");
        user.setAlpacaApiKey("updatedAlpacaApiKey");
        user.setAlpacaSecretKey("updatedAlpacaSecretKey");

        Integer updated = updateUser.updateUser(user);
        assertEquals(1, updated);

        User updatedUser = selectUser.selectUserById(user.getId());
        assertNotNull(updatedUser);
        assertEquals("updatedEmail@email.com", updatedUser.getEmail());
        assertEquals("updatedPassword", updatedUser.getPassword());
        assertEquals("updatedTradehookApiKey", updatedUser.getTradehookApiKey());
        assertEquals("updatedAlpacaApiKey", updatedUser.getAlpacaApiKey());
        assertEquals("updatedAlpacaSecretKey", updatedUser.getAlpacaSecretKey());
    }

    @Test
    public void updateUserThrowsNullExceptionWhenAttributeIsNull() {
        assertThrows(NullPointerException.class, () -> updateUser.updateUser(null));
    }

    @AfterAll
    public static void cleanup(@Autowired DeleteUser deleteUser) {
        deleteUser.DeleteUserByEmail("updatedEmail@email.com");
    }
}
