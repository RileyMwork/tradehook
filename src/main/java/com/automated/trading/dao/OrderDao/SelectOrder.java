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
    private DatabaseConnector databaseConnector;

    public List<Order> selectAllOrdersByUserId(Integer userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, userId, orderId, ticker, createdAt, side FROM Order_ WHERE userId = ? ORDER BY createdAt DESC";

        try (Connection conn = databaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String orderId = rs.getString("orderId");
                String ticker = rs.getString("ticker");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                String side = rs.getString("side");

                Order order = new Order(id, userId, orderId, ticker, createdAt, side);
                orders.add(order);
            }

            if (orders.isEmpty()) {
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
        String sql = "SELECT id, userId, orderId, ticker, createdAt, side FROM Order_ WHERE id = ?";

        try (Connection conn = databaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("userId");
                String orderId = rs.getString("orderId");
                String ticker = rs.getString("ticker");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                String side = rs.getString("side");

                order = new Order(id, userId, orderId, ticker, createdAt, side);
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

    public List<Order> selectAllOrdersByUserIdAndTickerSortedByCreatedAt(Integer userId, String ticker) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, userId, orderId, ticker, createdAt, side FROM Order_ WHERE userId = ? AND ticker = ? ORDER BY createdAt DESC";

        try (Connection conn = databaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, ticker);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String orderId = rs.getString("orderId");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                String side = rs.getString("side");

                Order order = new Order(id, userId, orderId, ticker, createdAt, side);
                orders.add(order);
            }

            if (orders.isEmpty()) {
                throw new SelectOrderDaoNoOrderFoundException("No Orders Found With UserID: " + userId + " And Ticker: " + ticker);
            }

            return orders;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Order> selectAllOrdersByUserIdAndTickerAndSide(Integer userId, String ticker, String side) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, userId, orderId, ticker, createdAt, side FROM Order_ WHERE userId = ? AND ticker = ? AND side = ? ORDER BY createdAt DESC";

        try (Connection conn = databaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, ticker);
            pstmt.setString(3, side);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String orderId = rs.getString("orderId");
                Timestamp createdAt = rs.getTimestamp("createdAt");

                Order order = new Order(id, userId, orderId, ticker, createdAt, side);
                orders.add(order);
            }

            if (orders.isEmpty()) {
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
        String sql = "SELECT id, userId, orderId, ticker, createdAt, side FROM Order_ WHERE userId = ? ORDER BY createdAt DESC LIMIT 1";

        try (Connection conn = databaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String orderId = rs.getString("orderId");
                String ticker = rs.getString("ticker");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                String side = rs.getString("side");

                order = new Order(id, userId, orderId, ticker, createdAt, side);
            }

            return order;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
