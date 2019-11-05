package com.huyi.demo.generate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    private Map<String,Connection> cacheConnection =new HashMap<String, Connection>();

    @Autowired
    DataSourceProperties dataSource;

    @Bean
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(dataSource.getDriverClassName());
        String url = dataSource.getUrl();
        String user = dataSource.getName();
        String password = dataSource.getPassword();
        Connection conn = null;
        if (cacheConnection .containsKey("conn")) {
            conn=cacheConnection.get("conn");
        }else{
            conn = DriverManager.getConnection(url, user, password);
            cacheConnection.put("conn",conn);
        }
        return conn;
    }
}
