package com.automated.trading.dao.OrderDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.automated.trading.util.DatabaseConnector;

@Component
public class DeleteOrder {
    
    @Autowired
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public Integer DeleteOrderById(int id) {
        String sql = "Delete FROM Order_ WHERE id = ?";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            Integer result = pstmt.executeUpdate();
            return result;
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer DeleteOrderByUserId(int userId) {
        String sql = "Delete FROM Order_ WHERE userId = ?";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            Integer result = pstmt.executeUpdate();
            return result;
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
