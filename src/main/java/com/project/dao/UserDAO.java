package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.project.entity.User;

public class UserDAO {

    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String tradehookApiKey = rs.getString("tradehookApiKey");
                String alpacaApiKey = rs.getString("alpacaApiKey");
                String alpacaSecretKey = rs.getString("alpacaSecretKey");

                User user = new User(id, email, password, tradehookApiKey, alpacaApiKey, alpacaSecretKey);
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
        return users;
    }

    public User selectUserById(int id) {
        User user = null;
        String sql = "SELECT * FROM user WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String tradehookApiKey = rs.getString("tradehookApiKey");
                    String alpacaApiKey = rs.getString("alpacaApiKey");
                    String alpacaSecretKey = rs.getString("alpacaSecretKey");

                    user = new User(id, email, password, tradehookApiKey, alpacaApiKey, alpacaSecretKey);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user with id " + id + ": " + e.getMessage());
        }

        return user;
    }

    public User selectUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM user WHERE email = ?";
    
        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {
    
            ps.setString(1, email); 
    
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String password = rs.getString("password");
                    String tradehookApiKey = rs.getString("tradehookApiKey");
                    String alpacaApiKey = rs.getString("alpacaApiKey");
                    String alpacaSecretKey = rs.getString("alpacaSecretKey");
                    
    
                    user = new User(id, email, password, tradehookApiKey, alpacaApiKey, alpacaSecretKey);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user with email " + email + ": " + e.getMessage());
        }
    
        return user;
    }

    public User insertNewUser(User user) {
        String sql = "INSERT INTO user (email, password, tradehookApiKey, alpacaApiKey, alpacaSecretKey) VALUES (?,?,?,?,?)";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getTradehookApiKey());
            ps.setString(4, user.getAlpacaApiKey());
            ps.setString(5, user.getAlpacaSecretKey());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int generatedUserId = rs.getInt(1);
                user.setId(generatedUserId); 
                return user;
            }

        } catch (SQLException e) {
            System.err.println("Error creating user with email: " + user.getEmail() + ". " + e.getMessage());
        }
        return null;
    }

    public void deleteUserById(int id) {
        String sql = "DELETE FROM user WHERE id = ?";

        try (Connection connection = DatabaseConnector.connect();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("User Deleted");

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    public User updateUserInfo(int id, String email, String password) {
        User user = selectUserById(id);
        String sql = "UPDATE user SET email = ?, password = ? WHERE id = ?";
    
        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {
    
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setInt(3, id);
    
            int rowsUpdated = ps.executeUpdate();
    
            if (rowsUpdated > 0) {
                User newUser = new User(id, email, password, user.getTradehookApiKey(), user.getAlpacaApiKey(), user.getAlpacaSecretKey());
                System.out.print("Updated User Information: ");
                return newUser;
            }
    
        } catch (SQLException e) {
            System.err.println("Error updating user with ID: " + id + ". " + e.getMessage());
        }
        return null;
    }

    public User updateUserTradehookApiKey(int id, String tradehookApiKey) {
        User user = selectUserById(id);
        String sql = "UPDATE user SET tradehookApiKey = ? WHERE id = ?";
    
        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {
    
                ps.setString(1, tradehookApiKey);
                ps.setInt(2, id);
    
            int rowsUpdated = ps.executeUpdate();
    
            if (rowsUpdated > 0) {
                User newUser = new User(id, user.getEmail(), user.getPassword(), tradehookApiKey, user.getAlpacaApiKey(), user.getAlpacaSecretKey());
                System.out.print("Updated User Information: ");
                return newUser;
            }
    
        } catch (SQLException e) {
            System.err.println("Error updating user with ID: " + id + ". " + e.getMessage());
        }
        return null;
    }
    
    public User updateUserAlpacaApiKeys(int id, String alpacaApiKey, String alpacaSecretKey) {
        User user = selectUserById(id);
        String sql = "UPDATE user SET alpacaApiKey = ?, alpacaSecretKey = ? WHERE id = ?";
    
        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {
    
                ps.setString(1, alpacaApiKey);
                ps.setString(2, alpacaSecretKey);
                ps.setInt(3, id);
    
            int rowsUpdated = ps.executeUpdate();
    
            if (rowsUpdated > 0) {
                User newUser = new User(id, user.getEmail(), user.getPassword(), user.getTradehookApiKey(), alpacaApiKey, alpacaSecretKey);
                System.out.print("Updated User Information: ");
                return newUser;
            }
    
        } catch (SQLException e) {
            System.err.println("Error updating user with ID: " + id + ". " + e.getMessage());
        }
        return null;
    }

}