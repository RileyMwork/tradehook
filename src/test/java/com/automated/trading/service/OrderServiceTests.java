package com.automated.trading.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.automated.trading.dao.OrderDao.DeleteOrder;
import com.automated.trading.dao.UserDao.DeleteUser;
import com.automated.trading.exception.daoexceptions.orderdaoexceptions.DeleteOrderDaoNoOrderToDeleteException;
import com.automated.trading.model.Order;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTests {
    
    @Autowired
    private OrderService orderService;

    private Order order1;
    private Order order2;
    private Order order3;
    private Order order4;
    private Order order5;
    private Order order6;
    private Order otherUserOrder;

    private String insertTicker = "INSERT";
    private String selectTicker = "SELECT";
    private String deleteTicker = "DELETE";

    private Timestamp now = Timestamp.valueOf(LocalDateTime.now());
    private Timestamp later = Timestamp.valueOf(LocalDateTime.now().plusSeconds(10));
    private Timestamp evenLater = Timestamp.valueOf(LocalDateTime.now().plusSeconds(20));

    private String filledQty = "1";

    private String filledAvgPrice = "2.50";

    private String orderType = "market";

    private String side = "buy";

    private String commission = null;

    @BeforeAll
    void setup() {
        order1 = new Order(1, insertTicker, now, now, filledQty, filledAvgPrice, orderType, side, commission);
        order2 = new Order(1, selectTicker, now, now, filledQty, filledAvgPrice, orderType, side, commission);
        order3 = new Order(1, selectTicker, later, later, filledQty, filledAvgPrice, orderType, side, commission);
        order4 = new Order(1, selectTicker, evenLater, evenLater, filledQty, filledAvgPrice, orderType, side, commission);
        order5 = new Order(1, selectTicker, evenLater, evenLater, filledQty, filledAvgPrice, orderType, "sell", commission);
        order6 = new Order(1, deleteTicker, now, now, filledQty, filledAvgPrice, orderType, side, commission);
        otherUserOrder = new Order(2, deleteTicker, now, now, filledQty, filledAvgPrice, orderType, side, commission);
    }

    @Test
    public void validOrderInfoReturnsCorrectOrder() {
        Order order = orderService.createNewOrder(order1);
        assertEquals(order1.getTicker(), order.getTicker());
    }

    @Test
    public void selectAllOrdersByUserIdReturnsCorrectListOfOrders() {
        orderService.createNewOrder(order2);
        orderService.createNewOrder(order3);
        orderService.createNewOrder(order4);
        List<Order> orders = orderService.selectAllOrdersByUserId(1);
        assertTrue(orders.size() == 3);

    }

    @Test
    public void selectAllOrdersByUserIdAndTickerReturnsCorrectListOfOrders() {
        orderService.createNewOrder(order1);
        orderService.createNewOrder(order2);
        orderService.createNewOrder(order3);
        orderService.createNewOrder(order4);
        List<Order> orders = orderService.selectAllOrdersByUserIdAndTickerSorted(1,selectTicker);
        assertTrue(orders.size() == 3);
    }

    @Test
    public void selectAllOrdersByUserIdAndTickerAndSideReturnsCorrectListOfOrders() {
        orderService.createNewOrder(order1);
        orderService.createNewOrder(order2);
        orderService.createNewOrder(order3);
        orderService.createNewOrder(order4);
        orderService.createNewOrder(order5);
        List<Order> orders = orderService.selectAllOrdersByUserIdAndTickerAndSideSorted(1,selectTicker,side);
        assertTrue(orders.size() == 3);
    }

    @Test
    public void deleteOrderByIdDeletesCorrectOrder() {
        orderService.createNewOrder(order1);
        orderService.createNewOrder(order6);
        List<Order> orderToDelete = orderService.selectAllOrdersByUserId(1);
        assertEquals(1,orderService.deleteOrderById(orderToDelete.get(0).getId()));
    }

    @Test
    public void deleteOrderByIdUserIdDeletesCorrectOrders() {
        orderService.createNewOrder(otherUserOrder);
        orderService.createNewOrder(order1);
        orderService.createNewOrder(order6);
        List<Order> ordersToDelete = orderService.selectAllOrdersByUserId(1);
        assertEquals(2,orderService.deleteOrdersByUserId(ordersToDelete.get(0).getUserId()));
    }

    @Test
    public void deleteOrderByIdUserIdAndTickerDeletesCorrectOrders() {
        orderService.createNewOrder(otherUserOrder);
        orderService.createNewOrder(order1);
        orderService.createNewOrder(order6);
        List<Order> ordersToDelete = orderService.selectAllOrdersByUserId(1);
        assertEquals(1,orderService.deleteOrdersByUserIdAndTicker(ordersToDelete.get(0).getUserId(), ordersToDelete.get(0).getTicker()));
    }

    @AfterEach
    public void cleanup(@Autowired DeleteOrder deleteOrder) {
        try {
            deleteOrder.deleteOrderByUserId(1);
        } catch (DeleteOrderDaoNoOrderToDeleteException e) {
            System.out.println(e.getMessage());
        }
        // deleteOrder.deleteOrderByUserId(2);
    }
}
