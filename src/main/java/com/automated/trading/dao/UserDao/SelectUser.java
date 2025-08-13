package com.automated.trading.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.automated.trading.model.User;
import com.automated.trading.util.DatabaseConnector;

@Component
public class SelectUser {

    @Autowired
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                String password = rs.getString(3);
                String tradehookApiKey = rs.getString(4);
                String alapcaApiKey = rs.getString(5);
                String alapcaSecretKey = rs.getString(6);

                User user = new User(id,email,password,tradehookApiKey,alapcaApiKey,alapcaSecretKey);
                users.add(user);
                
            }
            return users;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public User selectUserById(Integer id) {
        User user = null;
        String sql = "SELECT * FROM User WHERE id = ?";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String email = rs.getString(2);
                String password = rs.getString(3);
                String tradehookApiKey = rs.getString(4);
                String alapcaApiKey = rs.getString(5);
                String alapcaSecretKey = rs.getString(6);

                user = new User(id,email,password,tradehookApiKey,alapcaApiKey,alapcaSecretKey);
                
            }
            return user;

            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public User selectUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM User WHERE email = ?";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String password = rs.getString(3);
                String tradehookApiKey = rs.getString(4);
                String alapcaApiKey = rs.getString(5);
                String alapcaSecretKey = rs.getString(6);

                user = new User(id,email,password,tradehookApiKey,alapcaApiKey,alapcaSecretKey);
                
            }
            return user;

            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String selectUserEmailByEmail(String email) {
        String sql = "SELECT email FROM User WHERE email = ?";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            String fetchedEmail = rs.getString(1);
            
            return fetchedEmail;

            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
