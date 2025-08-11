package com.automated.trading.util;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest  // boots up Spring context with all beans
public class DatabaseConnectorTests {

    @Autowired
    private DatabaseConnector databaseConnector;

    @SuppressWarnings("unused")
    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnectReturnsValidConnection() throws SQLException {
        try (Connection conn = databaseConnector.connect()) {
            assertNotNull(conn, "Connection should not be null");
            assertFalse(conn.isClosed(), "Connection should be open");
        }
    }

    @Test
    public void testCloseClosesConnection() throws SQLException {
        Connection conn = databaseConnector.connect();
        assertFalse(conn.isClosed(), "Connection should be open before close");

        databaseConnector.close(conn);

        assertTrue(conn.isClosed(), "Connection should be closed after close()");
    }
}
