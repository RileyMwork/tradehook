package com.automated.trading.dao.UserDaoTests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.dao.UserDao.InsertUser;
import com.automated.trading.dao.UserDao.SelectUser;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.DeleteUserDaoNoUserToDelete;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.SelectUserDaoNoUsersFoundException;
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

    @Test
    public void deleteUserByIdReturnsOneAndNull() {
        String email = "delete_" + UUID.randomUUID() + "@test.com";
        User insertedUser = new User(email, "pw");
        insertUser.insertUser(insertedUser);
        User newUser = selectUser.selectUserByEmail(email);  // get ID
        Integer result = deleteUser.DeleteUserById(newUser.getId());

        assertEquals(1, result);
        assertThrows(SelectUserDaoNoUsersFoundException.class, 
                    () -> selectUser.selectUserById(newUser.getId()));
    }

    @Test
    public void deleteUserByEmailReturnsOneAndNull() {
        String email = "delete2_" + UUID.randomUUID() + "@test.com";
        User user = new User(email, "pw");
        insertUser.insertUser(user);
        user = selectUser.selectUserByEmail(email);
        Integer result = deleteUser.DeleteUserById(user.getId());

        assertEquals(1, result);
        assertThrows(SelectUserDaoNoUsersFoundException.class, 
                    () -> selectUser.selectUserByEmail(email));
    }

    @Test
    public void deleteUserThrowsNoUserToDeleteException() {
        String nullEmail = "ThisEmailDoesNotExist@nullemail.com";
        assertThrows(DeleteUserDaoNoUserToDelete.class, 
                    () -> deleteUser.DeleteUserByEmail(nullEmail));
    }
}
