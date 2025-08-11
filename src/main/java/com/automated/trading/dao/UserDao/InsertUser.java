package com.automated.trading.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.automated.trading.model.User;
import com.automated.trading.util.DatabaseConnector;

@Component
public class InsertUser {
    
    @Autowired
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public Integer insertUser(User user) {
        String sql = "INSERT INTO User (email, password, tradehookApiKey, alpacaApiKey, alpacaSecretKey) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getTradehookApiKey());
            pstmt.setString(4, user.getAlpacaApiKey());
            pstmt.setString(5, user.getAlpacaSecretKey());
            Integer result = pstmt.executeUpdate();
            return result;
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
