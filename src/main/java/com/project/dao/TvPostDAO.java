package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.project.entity.TvPost;

public class TvPostDAO {

    public TvPost insertNewTvPost(TvPost tvPost) {
        String sql = "INSERT INTO TvPost (userId, rawMessage, Date) VALUES (?,?,?)";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, tvPost.getUserId());
            ps.setString(2, tvPost.getRawMessage());
            ps.setTimestamp(3, tvPost.getDate());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int generatedTvPostId = rs.getInt(1);
                tvPost.setId(generatedTvPostId); 
                return tvPost;
            }

        } catch (SQLException e) {
            System.err.println("Error creating TvPost for user " + tvPost.getUserId() + ". " + e.getMessage());
        }
        return null;
    }

    public TvPost selectTvPostById(int id) {
        TvPost tvPost = null;
        String sql = "SELECT * FROM TvPost WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("userId");
                    String rawMessage = rs.getString("rawMessage");
                    Timestamp date = rs.getTimestamp("date");
                    

                    tvPost = new TvPost(id, userId, rawMessage, date);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving TvPost with id " + id + ": " + e.getMessage());
        }

        return tvPost;
    }

    public List<TvPost> selectAllTvPostsByUserId(int userId) {
        List<TvPost> tvPosts = new ArrayList<>();
        String sql = "SELECT * FROM TvPost WHERE userId = ?";

        try (Connection conn = DatabaseConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String rawMessage = rs.getString("rawMessage");
                    Timestamp date = rs.getTimestamp("date");
                    

                    TvPost tvPost = new TvPost(id, userId, rawMessage, date);
                    tvPosts.add(tvPost);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving TvPosts for user with id " + userId + ": " + e.getMessage());
        }

        return tvPosts;
    }

    public void deleteTvPostById(int id) {
        String sql = "DELETE FROM TvPost WHERE id = ?";

        try (Connection connection = DatabaseConnector.connect();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("TvPost Deleted with id: " + id + " deleted");

        } catch (SQLException e) {
            System.err.println("Error deleting TvPost: " + e.getMessage());
        }
    }

    public void deleteAllTvPostsByUserId(int userId) {
        String sql = "DELETE FROM TvPost WHERE userId = ?";

        try (Connection connection = DatabaseConnector.connect();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " TvPosts Deleted for user with id: " + userId);

        } catch (SQLException e) {
            System.err.println("Error deleting TvPost: " + e.getMessage());
        }
    }


}
