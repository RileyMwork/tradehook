package com.automated.trading.dao.UserDao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        insertedUser = selectUser.selectUserByEmail(email);  // get ID
    }

    @Test
    public void testSelectAllReturnsListLargerThanZero() {
        List<User> users = selectUser.selectAllUsers();
        assertTrue(users.size() > 0);
    }

    @Test
    public void testSelectByIdReturnsCorrectUser() {
        User found = selectUser.selectUserById(insertedUser.getId());
        assertEquals(insertedUser.getEmail(), found.getEmail());
    }

    @Test
    public void testSelectByEmailReturnsCorrectUser() {
        User found = selectUser.selectUserByEmail(insertedUser.getEmail());
        assertEquals(insertedUser.getId(), found.getId());
    }

    @AfterAll
    public static void cleanup(@Autowired DeleteUser deleteUser) {
        deleteUser.DeleteUserByEmail("selectTest@test.com");
    }
}
