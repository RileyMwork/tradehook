package com.automated.trading.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automated.trading.model.User;
import com.automated.trading.util.DatabaseConnector;

@Component
public class InsertUser {

    @Autowired
    private DatabaseConnector databaseConnector;

    public Integer insertUser(User user) {
        String sql = "INSERT INTO User (email, password, webhooktradingApiKey, alpacaApiKey, alpacaSecretKey) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = databaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getWebhookTradingApiKey());
            pstmt.setString(4, user.getAlpacaApiKey());
            pstmt.setString(5, user.getAlpacaSecretKey());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Inserting user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    user.setId(generatedId); // Set the ID on the user object
                    return rowsAffected;
                } else {
                    throw new SQLException("Inserting user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
            return null;
        }
    }
}
