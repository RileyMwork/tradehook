package com.automated.trading;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.dao.UserDao.InsertUser;
import com.automated.trading.dao.UserDao.SelectUser;
import com.automated.trading.dao.UserDao.UpdateUser;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.DeleteUserDaoNoUserToDelete;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.UpdateUserDaoNoUserFound;
import com.automated.trading.model.Order;
import com.automated.trading.model.User;
import com.automated.trading.service.OrderService;
import com.automated.trading.service.UserService;

@SpringBootApplication
public class TradingApplication {

    public static void main(String[] args) {
        
        ApplicationContext context = SpringApplication.run(TradingApplication.class, args);
        OrderService orderService = context.getBean(OrderService.class);
        Order order = new Order(1,"INSERTtestLATESTEVENLATEREST",Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()),"6","4.50","market","buy",null);
        Order createdOrder = orderService.createNewOrder(order);
        System.out.println(createdOrder);
    }

}
