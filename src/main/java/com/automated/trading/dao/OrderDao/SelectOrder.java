package com.automated.trading.dao.OrderDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automated.trading.exception.daoexceptions.orderdaoexceptions.SelectOrderDaoNoOrderFoundException;
import com.automated.trading.model.Order;
import com.automated.trading.util.DatabaseConnector;

@Component
public class SelectOrder {

    @Autowired
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    
    public List<Order> selectAllOrdersByUserId(Integer userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Order_ WHERE userId = ? ORDER BY filledAt DESC";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String ticker = rs.getString(3);
                Timestamp createdAt = rs.getTimestamp(4);
                Timestamp filledAt = rs.getTimestamp(5);
                String filledQty = rs.getString(6);
                String filledAvgPrice = rs.getString(7);
                String orderType = rs.getString(8);
                String side = rs.getString(9);
                String commission = rs.getString(10);

                Order order = new Order(id,userId,ticker,createdAt,filledAt,filledQty,filledAvgPrice,orderType,side,commission);
                orders.add(order);
                
            }
            if (orders.size() == 0) {
                throw new SelectOrderDaoNoOrderFoundException("No Orders In Database");
            }
            return orders;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Order selectOrderById(Integer id) {
        Order order = null;
        String sql = "SELECT * FROM Order_ WHERE id = ?";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt(2);
                String ticker = rs.getString(3);
                Timestamp createdAt = rs.getTimestamp(4);
                Timestamp filledAt = rs.getTimestamp(5);
                String filledQty = rs.getString(6);
                String filledAvgPrice = rs.getString(7);
                String orderType = rs.getString(8);
                String side = rs.getString(9);
                String commission = rs.getString(10);

                order = new Order(id,userId,ticker,createdAt,filledAt,filledQty,filledAvgPrice,orderType,side,commission);
                
            }
            if (order == null) {
                throw new SelectOrderDaoNoOrderFoundException("No Orders Found With ID: " + id);
            }
            return order;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Order> selectAllOrdersByUserIdAndTickerSortedByFilledAt(Integer userId,String ticker) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Order_ WHERE userId = ? AND ticker = ? ORDER BY filledAt DESC";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, ticker);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Timestamp createdAt = rs.getTimestamp(4);
                Timestamp filledAt = rs.getTimestamp(5);
                String filledQty = rs.getString(6);
                String filledAvgPrice = rs.getString(7);
                String orderType = rs.getString(8);
                String side = rs.getString(9);
                String commission = rs.getString(10);

                Order order = new Order(id,userId,ticker,createdAt,filledAt,filledQty,filledAvgPrice,orderType,side,commission);
                orders.add(order);
                
            }
            if (orders.size() == 0) {
                throw new SelectOrderDaoNoOrderFoundException("No Orders Found With UserID: " + userId + " And Ticker: " + ticker);
            }
            return orders;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Order> selectAllOrdersByUserIdAndTickerAndSide(Integer userId,String ticker, String side) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Order_ WHERE userId = ? AND ticker = ? AND side = ? ORDER BY filledAt DESC";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, ticker);
            pstmt.setString(3, side);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Timestamp createdAt = rs.getTimestamp(4);
                Timestamp filledAt = rs.getTimestamp(5);
                String filledQty = rs.getString(6);
                String filledAvgPrice = rs.getString(7);
                String orderType = rs.getString(8);
                String commission = rs.getString(10);

                Order order = new Order(id,userId,ticker,createdAt,filledAt,filledQty,filledAvgPrice,orderType,side,commission);
                orders.add(order);
                
            }
            if (orders.size() == 0) {
                throw new SelectOrderDaoNoOrderFoundException("No Orders Found With UserID: " + userId + " And Ticker: " + ticker + " And Side: " + side);
            }
            return orders;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Order selectLatestOrderByUserId(Integer userId) {
        Order order = null;
        String sql = "SELECT * FROM Order_ WHERE userId = ? ORDER BY filledAt DESC LIMIT 1";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            int id = rs.getInt(1);
            String ticker = rs.getString(3);
            Timestamp createdAt = rs.getTimestamp(4);
            Timestamp filledAt = rs.getTimestamp(5);
            String filledQty = rs.getString(6);
            String filledAvgPrice = rs.getString(7);
            String orderType = rs.getString(8);
            String side = rs.getString(9);
            String commission = rs.getString(10);
            

            order = new Order(id,userId,ticker,createdAt,filledAt,filledQty,filledAvgPrice,orderType,side,commission);
            return order;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}