package com.automated.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.dao.UserDao.InsertUser;
import com.automated.trading.dao.UserDao.SelectUser;
import com.automated.trading.dao.UserDao.UpdateUser;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.DeleteUserDaoNoUserToDelete;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.SelectUserDaoNoUsersFoundException;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.UpdateUserDaoNoUserFound;
import com.automated.trading.model.User;

@SpringBootApplication
public class TradingApplication {

    public static void main(String[] args) {
        
        ApplicationContext context = SpringApplication.run(TradingApplication.class, args);
        InsertUser insertUser = context.getBean(InsertUser.class);
        SelectUser selectUser = context.getBean(SelectUser.class);
        UpdateUser updateUser = context.getBean(UpdateUser.class);
        DeleteUser deleteUser = context.getBean(DeleteUser.class);
        User user = new User(1,"bob@example.com", "securepass456", "tradehook-key-2", "alpaca-key-2", "alpaca-secret-2");
        // insertUser.insertUser(null);
        // try {
        //     insertUser.insertUser(null);
        // } catch (InsertUserDaoNoUserCreatedException | InsertUserDaoMissingAttributeException e) {
        //     System.out.println(e.getMessage());
        // }

        // try {
        //     selectUser.selectUserByEmail("noemail@noemail.com");
        // } catch (SelectUserDaoNoUsersFoundException e) {
        //     System.out.println(e.getMessage());
        // }

        // try {
        //     User user = new User(1000,"nouserguyemail@email.com","nopass");
        //     updateUser.updateUser(user);
        // } catch (UpdateUserDaoNoUserFound e) {
        //     System.out.println(e.getMessage());
        // }

        try {
            deleteUser.DeleteUserByEmail("nodelete@nodelteemail.com");
        } catch (DeleteUserDaoNoUserToDelete e) {
            System.out.println(e.getMessage());
        }
    }

}
