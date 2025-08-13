package com.automated.trading.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automated.trading.exception.daoexceptions.userdaoexceptions.UpdateUserDaoNoUserFound;
import com.automated.trading.model.User;
import com.automated.trading.util.DatabaseConnector;

@Component
public class UpdateUser {

    @Autowired
    private DatabaseConnector databaseConnector;

    public Integer updateUser(User user) {
        String sql = "UPDATE User SET email = ?, password = ?, tradehookApiKey = ?, alpacaApiKey = ?, alpacaSecretKey = ? WHERE id = ?";

        try (Connection conn = databaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, user.getEmail());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getTradehookApiKey());
                pstmt.setString(4, user.getAlpacaApiKey());
                pstmt.setString(5, user.getAlpacaSecretKey());
                pstmt.setInt(6, user.getId());  // WHERE clause

                Integer result =  pstmt.executeUpdate();
                if (result == 0) {
                    throw new UpdateUserDaoNoUserFound("No User Found with email: " + user.getEmail());
                }
                return result;


        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return null;
        }
    }

}
