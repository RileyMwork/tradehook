package com.automated.trading.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import com.automated.trading.dao.OrderDao.DeleteOrder;
import com.automated.trading.exception.daoexceptions.orderdaoexceptions.DeleteOrderDaoNoOrderToDeleteException;
import com.automated.trading.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DeleteOrder deleteOrder;

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

    private Timestamp now;
    private Timestamp later;
    private Timestamp evenLater;

    private String sideBuy = "buy";
    private String sideSell = "sell";

    @BeforeAll
    void setup() {
        now = Timestamp.valueOf(LocalDateTime.now());
        later = Timestamp.valueOf(LocalDateTime.now().plusSeconds(10));
        evenLater = Timestamp.valueOf(LocalDateTime.now().plusSeconds(20));

        order1 = new Order(1, "ORD-1", insertTicker, now, sideBuy);
        order2 = new Order(1, "ORD-2", selectTicker, now, sideBuy);
        order3 = new Order(1, "ORD-3", selectTicker, later, sideBuy);
        order4 = new Order(1, "ORD-4", selectTicker, evenLater, sideBuy);
        order5 = new Order(1, "ORD-5", selectTicker, evenLater, sideSell);
        order6 = new Order(1, "ORD-6", deleteTicker, now, sideBuy);
        otherUserOrder = new Order(2, "ORD-7", deleteTicker, now, sideBuy);
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
        assertEquals(3, orders.size());
    }

    @Test
    public void selectAllOrdersByUserIdAndTickerReturnsCorrectListOfOrders() {
        orderService.createNewOrder(order1);
        orderService.createNewOrder(order2);
        orderService.createNewOrder(order3);
        orderService.createNewOrder(order4);
        List<Order> orders = orderService.selectAllOrdersByUserIdAndTickerSorted(1, selectTicker);
        assertEquals(3, orders.size());
    }

    @Test
    public void selectAllOrdersByUserIdAndTickerAndSideReturnsCorrectListOfOrders() {
        orderService.createNewOrder(order1);
        orderService.createNewOrder(order2);
        orderService.createNewOrder(order3);
        orderService.createNewOrder(order4);
        orderService.createNewOrder(order5);
        List<Order> orders = orderService.selectAllOrdersByUserIdAndTickerAndSideSorted(1, selectTicker, sideBuy);
        assertEquals(3, orders.size());
    }

    @Test
    public void deleteOrderByIdDeletesCorrectOrder() {
        orderService.createNewOrder(order1);
        orderService.createNewOrder(order6);
        List<Order> orders = orderService.selectAllOrdersByUserId(1);
        assertEquals(1, orderService.deleteOrderById(orders.get(0).getId()));
    }

    @Test
    public void deleteOrderByUserIdDeletesCorrectOrders() {
        orderService.createNewOrder(otherUserOrder);
        orderService.createNewOrder(order1);
        orderService.createNewOrder(order6);
        List<Order> ordersToDelete = orderService.selectAllOrdersByUserId(1);
        assertEquals(2, orderService.deleteOrdersByUserId(ordersToDelete.get(0).getUserId()));
    }

    @Test
    public void deleteOrderByUserIdAndTickerDeletesCorrectOrders() {
        orderService.createNewOrder(otherUserOrder);
        orderService.createNewOrder(order1);
        orderService.createNewOrder(order6);
        List<Order> ordersToDelete = orderService.selectAllOrdersByUserId(1);
        assertEquals(1, orderService.deleteOrdersByUserIdAndTicker(ordersToDelete.get(0).getUserId(), ordersToDelete.get(0).getTicker()));
    }

    @AfterEach
    public void cleanup() {
        try {
            deleteOrder.deleteOrderByUserId(1);
        } catch (DeleteOrderDaoNoOrderToDeleteException e) {
            System.out.println(e.getMessage());
        }

        try {
            deleteOrder.deleteOrderByUserId(2);
        } catch (DeleteOrderDaoNoOrderToDeleteException e) {
            System.out.println(e.getMessage());
        }
    }
}
