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
        Timestamp later = Timestamp.valueOf(LocalDateTime.now().plusSeconds(10));
        Timestamp moreLater = Timestamp.valueOf(LocalDateTime.now().plusSeconds(20));
        insertedOrder1 = new Order(1,"SELECT",Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()),"1","2.50","market","buy",null);
        insertedOrder2 = new Order(1,"SELECT",Timestamp.valueOf(LocalDateTime.now()),later,"1","5.50","market","sell",null);
        insertedOrder3 = new Order(1,"NULL",Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()),"1","5.50","market","buy",null);
        insertedOrder4 = new Order(1,"SELECT",Timestamp.valueOf(LocalDateTime.now()),moreLater,"1","5.50","market","buy",null);
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
        assertTrue(orders.size() == 4);
        assertTrue(orderUserIds.size() == 1);
    }

    @Test
    public void selectByIdReturnsCorrectOrder() {
        List<Order> orders = selectOrder.selectAllOrdersByUserId(1);
        Order expectedOrder = orders.get(0);
        Order actualOrder = selectOrder.selectOrderById(orders.get(0).getId());
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void selectAllOrdersByUserIdAndTickerSortedByFilledAtReturnsCorrectOrdersAndIsSorted() {
        List<Order> orders = selectOrder.selectAllOrdersByUserIdAndTickerSortedByFilledAt(1, "SELECT");
        assertTrue(orders.size() == 3);
        assertTrue(orders.get(0).getFilledAt().compareTo(orders.get(1).getFilledAt()) > 0);
    }

    @Test
    public void selectAllOrdersByUserIdAndTickerAndSideReturnsCorrectOrdersAndIsSorted() {
        List<Order> orders = selectOrder.selectAllOrdersByUserIdAndTickerAndSide(1, "SELECT", "buy");
        assertTrue(orders.size() == 2);
        assertTrue(orders.get(0).getFilledAt().compareTo(orders.get(1).getFilledAt()) > 0);
    }

    @Test
    public void selectLatestOrderByUserIdReturnsCorrectOrder() {
        List<Order> orders = selectOrder.selectAllOrdersByUserIdAndTickerSortedByFilledAt(insertedOrder4.getUserId(), insertedOrder4.getTicker());
        Order order = selectOrder.selectLatestOrderByUserId(insertedOrder4.getUserId());
        assertEquals(orders.get(0), order);
    }

    @Test 
    public void selectOrderThrowsExceptionWhenNoOrderIsFoundById() {
        assertThrows(SelectOrderDaoNoOrderFoundException.class, () -> selectOrder.selectOrderById(10000000));
    }

    @Test 
    public void selectOrderThrowsExceptionWhenNoOrderIsFoundByUserId() {
        assertThrows(SelectOrderDaoNoOrderFoundException.class, () -> selectOrder.selectAllOrdersByUserId(1000000));
    }

    @Test 
    public void selectOrderThrowsExceptionWhenNoOrderIsFoundByUserIdAndTicker() {
        assertThrows(SelectOrderDaoNoOrderFoundException.class, () -> selectOrder.selectAllOrdersByUserIdAndTickerSortedByFilledAt(10000000, "NULLTICKER"));
    }

    @Test 
    public void selectOrderThrowsExceptionWhenNoOrderIsFoundByUserIdAndTickerAndSide() {
        assertThrows(SelectOrderDaoNoOrderFoundException.class, () -> selectOrder.selectAllOrdersByUserIdAndTickerAndSide(10000000, "NULLTICKER", "buy"));
    }

    @AfterAll
    public static void cleanup(@Autowired DeleteOrder deleteOrder) {
        deleteOrder.deleteOrderByUserId(1);
    }
}
