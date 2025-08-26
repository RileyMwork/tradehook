package com.automated.trading.dao.OrderDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automated.trading.model.Order;
import com.automated.trading.util.DatabaseConnector;

@Component
public class InsertOrder {

    @Autowired
    private DatabaseConnector databaseConnector;

    public Integer insertOrder(Order order) {
        // Updated SQL to include qty
        String sql = "INSERT INTO Order_ (userId, orderId, ticker, qty, createdAt, side) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = databaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set the values for the PreparedStatement, including qty
            pstmt.setInt(1, order.getUserId());
            pstmt.setString(2, order.getOrderId());
            pstmt.setString(3, order.getTicker());
            pstmt.setString(4, order.getQty());  // Setting qty
            pstmt.setTimestamp(5, order.getCreatedAt());
            pstmt.setString(6, order.getSide());

            // Execute the update
            return pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
