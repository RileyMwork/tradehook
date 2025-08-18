package com.automated.trading.dao.OrderDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automated.trading.exception.daoexceptions.orderdaoexceptions.DeleteOrderDaoNoOrderToDeleteException;
import com.automated.trading.util.DatabaseConnector;

@Component
public class DeleteOrder {
    
    @Autowired
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public Integer deleteOrderById(int id) {
        String sql = "Delete FROM Order_ WHERE id = ?";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            Integer result = pstmt.executeUpdate();
            if (result == 0) {
                throw new DeleteOrderDaoNoOrderToDeleteException("No Order To Delete With ID: " + id);
            }
            return result;
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer deleteOrderByUserId(int userId) {
        String sql = "Delete FROM Order_ WHERE userId = ?";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            Integer result = pstmt.executeUpdate();
            if (result == 0) {
                throw new DeleteOrderDaoNoOrderToDeleteException("No Orders To Delete With UserID: " + userId);
            }
            return result;
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer deleteOrderByUserIdAndTicker(int userId, String ticker) {
        String sql = "Delete FROM Order_ WHERE userId = ? AND ticker = ?";
        try (Connection conn = databaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, ticker);

            Integer result = pstmt.executeUpdate();
            if (result == 0) {
                throw new DeleteOrderDaoNoOrderToDeleteException("No Order To Delete With UserID: " + userId + " And Ticker: " + ticker);
            }
            return result;
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }




}
