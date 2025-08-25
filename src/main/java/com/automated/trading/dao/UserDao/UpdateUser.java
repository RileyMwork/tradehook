package com.automated.trading.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.automated.trading.exception.daoexceptions.userdaoexceptions.UpdateUserDaoNoUserFound;
import com.automated.trading.util.DatabaseConnector;
import com.automated.trading.model.User;  // Import the User model class

@Component
public class UpdateUser {

    @Autowired
    private DatabaseConnector databaseConnector;

    // Method to update Alpaca API keys
    public Integer updateUserAlpacaKeys(User user) {
        String sql = "UPDATE User SET alpacaApiKey = ?, alpacaSecretKey = ? WHERE email = ?";

        try (Connection conn = databaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Use the User object to get the keys and email
            pstmt.setString(1, user.getAlpacaApiKey());
            pstmt.setString(2, user.getAlpacaSecretKey());
            pstmt.setString(3, user.getEmail());

            Integer result = pstmt.executeUpdate();
            if (result == 0) {
                throw new UpdateUserDaoNoUserFound("No User Found with email: " + user.getEmail());
            }
            return result;

        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return null;
        }
    }

    // Method to update the Tradehook API key
    public Integer updateUserTradehookKey(User user) {
        String sql = "UPDATE User SET tradehookApiKey = ? WHERE email = ?";

        try (Connection conn = databaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Use the User object to get the tradehook API key and email
            pstmt.setString(1, user.getTradehookApiKey());
            pstmt.setString(2, user.getEmail());

            Integer result = pstmt.executeUpdate();
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
