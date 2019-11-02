package com.huyi.mybatisgenerate.demo.generate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {

    @Autowired
    DataSourceProperties dataSource;

    @Bean
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(dataSource.getDriverClassName());
        String url = dataSource.getUrl();
        String user = dataSource.getName();
        String password = dataSource.getPassword();
        Connection conn = null;
        if (conn == null) {
            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }
}
