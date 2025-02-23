package com.project.repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnector {

    @Autowired
    private DataSource dataSource;

    // Method to establish a database connection
    public Connection connect() throws SQLException {
        return dataSource.getConnection();
    }

    // Method to close a database connection
    public void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}
