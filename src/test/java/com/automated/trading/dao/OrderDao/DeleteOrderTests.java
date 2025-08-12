package com.automated.trading.dao.OrderDao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.automated.trading.model.Order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteOrderTests {

    @Autowired
    private InsertOrder insertOrder;

    @Autowired
    private DeleteOrder deleteOrder;

    @Autowired
    private SelectOrder selectOrder;

    private Order order1;
    private Order order2;
    private Order order3;

    @BeforeEach
    void setup() {
        order1 = new Order(1, "DELTEST", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), "1", "2.50", "market", "buy", null);
        order2 = new Order(1, "DELTEST", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), "1", "3.50", "market", "sell", null);
        order3 = new Order(2, "OTHERUSER", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), "1", "4.50", "market", "buy", null);

        insertOrder.insertOrder(order1);
        insertOrder.insertOrder(order2);
        insertOrder.insertOrder(order3);
    }

    @AfterEach
    void cleanup() {
        deleteOrder.DeleteOrderByUserId(1);
        deleteOrder.DeleteOrderByUserId(2);
    }

    @Test
    public void deleteOrderByIdDeletesSingleOrder() {
        List<Order> orders = selectOrder.selectAllOrdersByUserId(1);
        assertFalse(orders.isEmpty());

        int deletedCount = deleteOrder.DeleteOrderById(orders.get(0).getId());
        assertEquals(1, deletedCount);

        List<Order> remainingOrders = selectOrder.selectAllOrdersByUserId(1);
        assertEquals(1, remainingOrders.size());
    }

    @Test
    public void deleteOrderByUserIdDeletesAllOrdersForUser() {
        int deletedCount = deleteOrder.DeleteOrderByUserId(1);
        assertEquals(2, deletedCount);

        List<Order> remainingOrders = selectOrder.selectAllOrdersByUserId(1);
        assertTrue(remainingOrders.isEmpty());
    }

    @Test
    public void deleteOrderByUserIdAndTickerDeletesOnlyMatchingOrders() {
        int deletedCount = deleteOrder.DeleteOrderByUserIdAndTicker(1, "DELTEST");
        assertEquals(2, deletedCount);

        List<Order> remainingOrders = selectOrder.selectAllOrdersByUserId(1);
        assertTrue(remainingOrders.isEmpty());
    }
}
