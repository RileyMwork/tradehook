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
            return selectOrder.selectAllOrdersByUserId(userId);
        } catch (SelectOrderDaoNoOrderFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Order> selectAllOrdersByUserIdAndTickerSorted(Integer userId, String ticker) {
        try {
            return selectOrder.selectAllOrdersByUserIdAndTickerSortedByCreatedAt(userId, ticker); // Renamed method
        } catch (SelectOrderDaoNoOrderFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Order> selectAllOrdersByUserIdAndTickerAndSideSorted(Integer userId, String ticker, String side) {
        try {
            return selectOrder.selectAllOrdersByUserIdAndTickerAndSide(userId, ticker, side);
        } catch (SelectOrderDaoNoOrderFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer deleteOrderById(Integer id) {
        try {
            return deleteOrder.deleteOrderById(id);
        } catch (DeleteOrderDaoNoOrderToDeleteException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer deleteOrdersByUserId(Integer userId) {
        try {
            return deleteOrder.deleteOrderByUserId(userId);
        } catch (DeleteOrderDaoNoOrderToDeleteException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer deleteOrdersByUserIdAndTicker(Integer userId, String ticker) {
        try {
            return deleteOrder.deleteOrderByUserIdAndTicker(userId, ticker); // fixed typo from "ticekr"
        } catch (DeleteOrderDaoNoOrderToDeleteException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
