package com.automated.trading;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.automated.trading.dao.OrderDao.DeleteOrder;
import com.automated.trading.dao.OrderDao.InsertOrder;
import com.automated.trading.dao.OrderDao.SelectOrder;
import com.automated.trading.model.Order;


@SpringBootApplication
public class TradingApplication {

    public static void main(String[] args) {
        // var context = SpringApplication.run(TradingApplication.class, args);

        // InsertOrder insertDao = context.getBean(InsertOrder.class);
        // SelectOrder selectDao = context.getBean(SelectOrder.class);
        // DeleteOrder deleteDao = context.getBean(DeleteOrder.class);

        // Order order = new Order(1,"LTCUSD",
        //     Timestamp.valueOf(LocalDateTime.now()),
        //     Timestamp.valueOf(LocalDateTime.now()),
        //     "2000", "1.50", "market", "buy", null
        // );

        // int insertedOrder = insertDao.insertOrder(order);
        // System.out.println(insertedOrder);

        // List<Order> orders = selectDao.selectAllOrdersByUserIdAndTickerSortedByFilledAt(1,"LTCUSD");
        // System.out.println(orders);
        // Order selectedOrder = selectDao.selectOrderById(orders.get(0).getId());
        // int deletedOrder = deleteDao.DeleteOrderById(1);
        // int deletedOrders = deleteDao.DeleteOrderByUserId(1);
        // System.out.println(deletedOrders);

    }

}
