package com.automated.trading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.automated.trading.dao.OrderDao.DeleteOrder;
import com.automated.trading.dao.OrderDao.InsertOrder;
import com.automated.trading.dao.OrderDao.SelectOrder;
import com.automated.trading.exception.daoexceptions.orderdaoexceptions.DeleteOrderDaoNoOrderToDeleteException;
import com.automated.trading.exception.daoexceptions.orderdaoexceptions.SelectOrderDaoNoOrderFoundException;
import com.automated.trading.model.Order;

@Component
public class OrderService {
    
    @Autowired
    private InsertOrder insertOrder;

    @Autowired
    private SelectOrder selectOrder;

    @Autowired
    private DeleteOrder deleteOrder;

    public Order createNewOrder(Order order) {
        insertOrder.insertOrder(order); 
        Order createdOrder = selectOrder.selectLatestOrderByUserId(order.getUserId());
        return createdOrder;
    }

    public List<Order> selectAllOrdersByUserId(Integer userId) {
        try {
            List<Order> orders = selectOrder.selectAllOrdersByUserId(userId);
            return orders;
        } catch (SelectOrderDaoNoOrderFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Order> selectAllOrdersByUserIdAndTickerSorted(Integer userId, String ticker) {
        try {
            List<Order> orders = selectOrder.selectAllOrdersByUserIdAndTickerSortedByFilledAt(userId, ticker);
            return orders;
        } catch (SelectOrderDaoNoOrderFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Order> selectAllOrdersByUserIdAndTickerAndSideSorted(Integer userId, String ticker, String side) {
        try {
            List<Order> orders = selectOrder.selectAllOrdersByUserIdAndTickerAndSide(userId, ticker, side);
            return orders;
        } catch (SelectOrderDaoNoOrderFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer deleteOrderById(Integer id) {
        try {
            Integer deletedOrderRow = deleteOrder.deleteOrderById(id);
            return deletedOrderRow;
        } catch (DeleteOrderDaoNoOrderToDeleteException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer deleteOrdersByUserId(Integer userId) {
        try {
            Integer deletedOrderRow = deleteOrder.deleteOrderByUserId(userId);
            return deletedOrderRow;
        } catch (DeleteOrderDaoNoOrderToDeleteException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer deleteOrdersByUserIdAndTicker(Integer userId, String ticekr) {
        try {
            Integer deletedOrderRow = deleteOrder.deleteOrderByUserIdAndTicker(userId,ticekr);
            return deletedOrderRow;
        } catch (DeleteOrderDaoNoOrderToDeleteException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
