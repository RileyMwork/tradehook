package com.automated.trading.dao.OrderDaoTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.automated.trading.dao.OrderDao.DeleteOrder;
import com.automated.trading.dao.OrderDao.InsertOrder;
import com.automated.trading.dao.OrderDao.SelectOrder;
import com.automated.trading.exception.daoexceptions.orderdaoexceptions.SelectOrderDaoNoOrderFoundException;
import com.automated.trading.model.Order;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SelectOrderTests {

    @Autowired
    private InsertOrder insertOrder;

    @Autowired
    private SelectOrder selectOrder;

    private Order insertedOrder1;
    private Order insertedOrder2;
    private Order insertedOrder3;
    private Order insertedOrder4;

    @BeforeAll
    void setup() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Timestamp later = Timestamp.valueOf(LocalDateTime.now().plusSeconds(10));
        Timestamp moreLater = Timestamp.valueOf(LocalDateTime.now().plusSeconds(20));

        insertedOrder1 = new Order(1, "TESTORDERID1", "SELECT","1", now, "buy");
        insertedOrder2 = new Order(1, "TESTORDERID2", "SELECT","1", later, "sell");
        insertedOrder3 = new Order(1, "TESTORDERID3", "NULL","1", now, "buy");
        insertedOrder4 = new Order(1, "TESTORDERID4", "SELECT","1", moreLater, "buy");

        insertOrder.insertOrder(insertedOrder1);
        insertOrder.insertOrder(insertedOrder2);
        insertOrder.insertOrder(insertedOrder3);
        insertOrder.insertOrder(insertedOrder4);
    }

    @Test
    public void selectAllByUserIdReturnsListLargerThanZero() {
        List<Order> orders = selectOrder.selectAllOrdersByUserId(1);
        Set<Integer> orderUserIds = new HashSet<>();
        for (Order order : orders) {
            orderUserIds.add(order.getUserId());
        }
        assertEquals(4, orders.size());
        assertEquals(1, orderUserIds.size());
    }

    @Test
    public void selectByIdReturnsCorrectOrder() {
        List<Order> orders = selectOrder.selectAllOrdersByUserId(1);
        Order expectedOrder = orders.get(0);
        Order actualOrder = selectOrder.selectOrderById(expectedOrder.getId());
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void selectAllOrdersByUserIdAndTickerSortedByCreatedAtReturnsCorrectOrdersAndIsSorted() {
        List<Order> orders = selectOrder.selectAllOrdersByUserIdAndTickerSortedByCreatedAt(1, "SELECT");
        assertEquals(3, orders.size());
        assertTrue(orders.get(0).getCreatedAt().compareTo(orders.get(1).getCreatedAt()) >= 0);
        assertTrue(orders.get(1).getCreatedAt().compareTo(orders.get(2).getCreatedAt()) >= 0);
    }

    @Test
    public void selectAllOrdersByUserIdAndTickerAndSideReturnsCorrectOrdersAndIsSorted() {
        List<Order> orders = selectOrder.selectAllOrdersByUserIdAndTickerAndSide(1, "SELECT", "buy");
        assertEquals(2, orders.size());
        assertTrue(orders.get(0).getCreatedAt().compareTo(orders.get(1).getCreatedAt()) >= 0);
    }

    @Test
    public void selectLatestOrderByUserIdReturnsCorrectOrder() {
        List<Order> orders = selectOrder.selectAllOrdersByUserIdAndTickerSortedByCreatedAt(insertedOrder4.getUserId(), insertedOrder4.getTicker());
        Order latest = selectOrder.selectLatestOrderByUserId(insertedOrder4.getUserId());
        assertEquals(orders.get(0), latest);
    }

    @Test 
    public void selectOrderThrowsExceptionWhenNoOrderIsFoundById() {
        assertThrows(SelectOrderDaoNoOrderFoundException.class, () -> selectOrder.selectOrderById(9999999));
    }

    @Test 
    public void selectOrderThrowsExceptionWhenNoOrderIsFoundByUserId() {
        assertThrows(SelectOrderDaoNoOrderFoundException.class, () -> selectOrder.selectAllOrdersByUserId(9999999));
    }

    @Test 
    public void selectOrderThrowsExceptionWhenNoOrderIsFoundByUserIdAndTicker() {
        assertThrows(SelectOrderDaoNoOrderFoundException.class, () -> selectOrder.selectAllOrdersByUserIdAndTickerSortedByCreatedAt(9999999, "NULLTICKER"));
    }

    @Test 
    public void selectOrderThrowsExceptionWhenNoOrderIsFoundByUserIdAndTickerAndSide() {
        assertThrows(SelectOrderDaoNoOrderFoundException.class, () -> selectOrder.selectAllOrdersByUserIdAndTickerAndSide(9999999, "NULLTICKER", "buy"));
    }

    @AfterAll
    public static void cleanup(@Autowired DeleteOrder deleteOrder) {
        deleteOrder.deleteOrderByUserId(1);
    }
}
