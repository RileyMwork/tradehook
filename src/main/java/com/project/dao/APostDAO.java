package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.project.entity.APost;

public class APostDAO {
    
    public APost insertNewTvPost(APost aPost) {
        String sql = "INSERT INTO APost (userId, tvPostId, rawMessage, date, ticker, orderType, side, isSuccessful, avgEntryPrice, qty, timeInForce, orderId, clientOrderId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, aPost.getUserId());
            ps.setInt(2, aPost.getTvPostId());
            ps.setString(3, aPost.getRawMessage());
            ps.setTimestamp(4, aPost.getDate());
            ps.setString(5, aPost.getTicker());
            ps.setString(6, aPost.getOrderType());
            ps.setString(7, aPost.getSide());
            ps.setBoolean(8, aPost.getIsSuccessful());
            ps.setDouble(9, aPost.getAvgEntryPrice());
            ps.setDouble(10, aPost.getQty());
            ps.setString(11, aPost.getTimeInForce());
            ps.setString(12, aPost.getOrderId());
            ps.setString(13, aPost.getClientOrderId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int generatedAPostId = rs.getInt(1);
                aPost.setId(generatedAPostId); 
                return aPost;
            }

        } catch (SQLException e) {
            System.err.println("Error creating APost for user " + aPost.getUserId() + ". " + e.getMessage());
        }
        return null;
    }

    public APost selectTvPostById(int id) {
        APost aPost = null;
        String sql = "SELECT * FROM APost WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("userId");
                    int tvPostId = rs.getInt("tvPostId");
                    String rawMessage = rs.getString("rawMessage");
                    Timestamp date = rs.getTimestamp("date");
                    String ticker = rs.getString("ticker");
                    String orderType = rs.getString("orderType");
                    String side = rs.getString("side");
                    boolean isSuccessful = rs.getBoolean("isSuccessful");
                    Double avgEntryPrice = rs.getDouble("avgEntryPrice");
                    Double qty = rs.getDouble("qty");
                    String timeInForce = rs.getString("timeInForce");
                    String orderId = rs.getString("orderId");
                    String clientOrderId = rs.getString("clientOrderId");
                    

                    aPost = new APost(id, userId, tvPostId, rawMessage, date, ticker, orderType, side, isSuccessful, avgEntryPrice, qty, timeInForce, orderId, clientOrderId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving APost with id " + id + ": " + e.getMessage());
        }

        return aPost;
    }

    public List<APost> selectAllTvPostsByUserId(int userId) {
        List<APost> aPosts = new ArrayList<>();
        String sql = "SELECT * FROM APost WHERE userId = ?";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int tvPostId = rs.getInt("tvPostId");
                    String rawMessage = rs.getString("rawMessage");
                    Timestamp date = rs.getTimestamp("date");
                    String ticker = rs.getString("ticker");
                    String orderType = rs.getString("orderType");
                    String side = rs.getString("side");
                    boolean isSuccessful = rs.getBoolean("isSuccessful");
                    Double avgEntryPrice = rs.getDouble("avgEntryPrice");
                    Double qty = rs.getDouble("qty");
                    String timeInForce = rs.getString("timeInForce");
                    String orderId = rs.getString("orderId");
                    String clientOrderId = rs.getString("clientOrderId");
                    

                    APost aPost = new APost(id, userId, tvPostId, rawMessage, date, ticker, orderType, side, isSuccessful, avgEntryPrice, qty, timeInForce, orderId, clientOrderId);
                    aPosts.add(aPost);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving APosts for user with id " + userId + ": " + e.getMessage());
        }

        return aPosts;
    }

    public void deleteAPostById(int id) {
        String sql = "DELETE FROM APost WHERE id = ?";

        try (Connection connection = DatabaseConnector.connect();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("APost Deleted with id: " + id + " deleted");

        } catch (SQLException e) {
            System.err.println("Error deleting APost: " + e.getMessage());
        }
    }

    public void deleteAllTvPostsByUserId(int userId) {
        String sql = "DELETE FROM APost WHERE userId = ?";

        try (Connection connection = DatabaseConnector.connect();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " APosts Deleted for user with id: " + userId);

        } catch (SQLException e) {
            System.err.println("Error deleting APosts: " + e.getMessage());
        }
    }

}
