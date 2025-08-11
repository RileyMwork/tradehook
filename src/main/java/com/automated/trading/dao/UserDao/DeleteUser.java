package com.automated.trading.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.automated.trading.util.DatabaseConnector;

@Component
public class DeleteUser {
    
    @Autowired
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public Integer DeleteUserById(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
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

    public Integer DeleteUserByEmail(String email) {
        String sql = "DELETE FROM User WHERE email = ?";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            Integer result = pstmt.executeUpdate();
            return result;
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
