package com.automated.trading.dao.OrderDaoTests;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.automated.trading.dao.OrderDao.DeleteOrder;
import com.automated.trading.dao.OrderDao.InsertOrder;
import com.automated.trading.dao.OrderDao.SelectOrder;
import com.automated.trading.model.Order;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InsertOrderTests {

    @Autowired
    private InsertOrder insertOrder;

    @Autowired
    private SelectOrder selectOrder;

    @Test
    public void insertOrderReturnsOneAndCorrectTicker() {
        Order order = new Order(1,"INSERT",Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()),"1","2.50","market","buy",null);
        Integer result = insertOrder.insertOrder(order);
        List<Order> selected = selectOrder.selectAllOrdersByUserIdAndTickerSortedByFilledAt(1,"INSERT");
        assertEquals(1, result);
        assertNotNull(selected);
        assertEquals("INSERT", selected.get(0).getTicker());
    }

    @Test 
    public void insertOrderThrowsNullExceptionWhenParameterIsNull() {
        assertThrows(NullPointerException.class, () -> insertOrder.insertOrder(null));
    }

    @AfterAll
    public static void cleanup(@Autowired DeleteOrder deleteOrder) {
        deleteOrder.DeleteOrderByUserId(1);
    }
}