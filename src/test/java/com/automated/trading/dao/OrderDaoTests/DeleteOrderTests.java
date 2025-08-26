package com.automated.trading.dao.OrderDaoTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.automated.trading.dao.OrderDao.DeleteOrder;
import com.automated.trading.dao.OrderDao.InsertOrder;
import com.automated.trading.dao.OrderDao.SelectOrder;
import com.automated.trading.exception.daoexceptions.orderdaoexceptions.DeleteOrderDaoNoOrderToDeleteException;
import com.automated.trading.exception.daoexceptions.orderdaoexceptions.SelectOrderDaoNoOrderFoundException;
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
        order1 = new Order(1, "DELTEST-1", "DELTEST", "1", Timestamp.valueOf(LocalDateTime.now()), "buy");
        order2 = new Order(1, "DELTEST-2", "DELTEST", "1", Timestamp.valueOf(LocalDateTime.now()), "sell");
        order3 = new Order(2, "DELTEST-3", "OTHERUSER", "1", Timestamp.valueOf(LocalDateTime.now()), "buy");

        insertOrder.insertOrder(order1);
        insertOrder.insertOrder(order2);
        insertOrder.insertOrder(order3);
    }

    @AfterEach
    void cleanup() {
        try {
            deleteOrder.deleteOrderByUserId(1);
        } catch (DeleteOrderDaoNoOrderToDeleteException ignored) {}

        try {
            deleteOrder.deleteOrderByUserId(2);
        } catch (DeleteOrderDaoNoOrderToDeleteException ignored) {}
    }

    @Test
    public void deleteOrderByIdDeletesSingleOrder() {
        List<Order> orders = selectOrder.selectAllOrdersByUserId(1);
        assertFalse(orders.isEmpty());

        int deletedCount = deleteOrder.deleteOrderById(orders.get(0).getId());
        assertEquals(1, deletedCount);

        List<Order> remainingOrders = selectOrder.selectAllOrdersByUserId(1);
        assertEquals(1, remainingOrders.size());
    }

    @Test
    public void deleteOrderByUserIdDeletesAllOrdersForUser() {
        int deletedCount = deleteOrder.deleteOrderByUserId(1);
        assertEquals(2, deletedCount);
        assertThrows(SelectOrderDaoNoOrderFoundException.class, () -> selectOrder.selectAllOrdersByUserId(1));
    }

    @Test
    public void deleteOrderByUserIdAndTickerDeletesOnlyMatchingOrders() {
        int deletedCount = deleteOrder.deleteOrderByUserIdAndTicker(1, "DELTEST");
        assertEquals(2, deletedCount);
        assertThrows(SelectOrderDaoNoOrderFoundException.class, () -> selectOrder.selectAllOrdersByUserId(1));
    }

    @Test
    public void deleteOrderThrowsExceptionWhenNoOrderIsFoundById() {
        assertThrows(DeleteOrderDaoNoOrderToDeleteException.class, () -> deleteOrder.deleteOrderById(1000000));
    }

    @Test
    public void deleteOrderThrowsExceptionWhenNoOrderIsFoundByUserId() {
        assertThrows(DeleteOrderDaoNoOrderToDeleteException.class, () -> deleteOrder.deleteOrderByUserId(1000000));
    }

    @Test
    public void deleteOrderThrowsExceptionWhenNoOrderIsFoundByUserIdAndTicker() {
        assertThrows(DeleteOrderDaoNoOrderToDeleteException.class, () -> deleteOrder.deleteOrderByUserIdAndTicker(1000000, "NULLTICKER"));
    }
}
