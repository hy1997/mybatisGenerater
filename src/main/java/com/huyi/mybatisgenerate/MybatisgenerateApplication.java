package com.huyi.mybatisgenerate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

@SpringBootApplication
public class MybatisgenerateApplication {
    @Autowired
    DataSourceProperties dataSource;


    public static void main(String[] args) {
        SpringApplication.run(MybatisgenerateApplication.class, args);
    }

}
