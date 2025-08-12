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
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public Integer insertOrder(Order order) {
        String sql = "INSERT INTO Order_ (userId, ticker, createdAt, filledAt, filledQty, filledAvgPrice, orderType, side, commission) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // pstmt.setInt(1, order.getId());
            pstmt.setInt(1, order.getUserId());
            pstmt.setString(2, order.getTicker());
            pstmt.setTimestamp(3, order.getCreatedAt());
            pstmt.setTimestamp(4, order.getFilledAt());
            pstmt.setString(5, order.getFilledQty());
            pstmt.setString(6, order.getFilledAvgPrice());
            pstmt.setString(7, order.getOrderType());
            pstmt.setString(8, order.getSide());
            pstmt.setString(9, order.getCommission());

            Integer result = pstmt.executeUpdate();
            return result;
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
