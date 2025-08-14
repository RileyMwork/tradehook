package com.automated.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.dao.UserDao.InsertUser;
import com.automated.trading.dao.UserDao.SelectUser;
import com.automated.trading.dao.UserDao.UpdateUser;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.DeleteUserDaoNoUserToDelete;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.UpdateUserDaoNoUserFound;
import com.automated.trading.model.User;
import com.automated.trading.service.UserService;

@SpringBootApplication
public class TradingApplication {

    public static void main(String[] args) {
        
        ApplicationContext context = SpringApplication.run(TradingApplication.class, args);
        UserService userService = context.getBean(UserService.class);
        User user = new User("testingNewUserGuy@mail.com","TESTINGPASSWORD");
        userService.createNewUser(user);
    }

}
