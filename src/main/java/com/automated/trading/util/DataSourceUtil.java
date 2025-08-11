package com.automated.trading.util;

import org.sqlite.SQLiteDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DataSourceUtil {

    @Bean
    public DataSource dataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:C:/Users/riley/OneDrive/Desktop/full_trading_project_java/trading/trading/src/main/resources/automated_trading.db");

        return dataSource;
    }
}


