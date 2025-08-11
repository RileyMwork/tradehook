package com.automated.trading.dao.UserDao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.automated.trading.model.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteUserTests {

    @Autowired
    private InsertUser insertUser;

    @Autowired
    private SelectUser selectUser;

    @Autowired
    private DeleteUser deleteUser;

    private User insertedUser;

    @BeforeAll
    void setup() {
        String email = "delete_" + UUID.randomUUID() + "@test.com";
        insertedUser = new User(email, "pw");
        insertUser.insertUser(insertedUser);
        insertedUser = selectUser.selectUserByEmail(email);  // get ID
    }

    @Test
    public void deleteUserByIdReturnsOneAndNull() {
        Integer result = deleteUser.DeleteUserById(insertedUser.getId());
        User found = selectUser.selectUserById(insertedUser.getId());

        assertEquals(1, result);
        assertNull(found);
    }

    @Test
    public void deleteUserByEmailReturnsOneAndNull() {
        // Setup another user
        String email = "delete2_" + UUID.randomUUID() + "@test.com";
        User user = new User(email, "pw");
        insertUser.insertUser(user);
        user = selectUser.selectUserByEmail(email);

        Integer result = deleteUser.DeleteUserById(user.getId());
        User found = selectUser.selectUserByEmail(email);

        assertEquals(1, result);
        assertNull(found);
    }
}
