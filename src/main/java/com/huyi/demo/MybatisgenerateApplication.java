package com.huyi.demo;

import com.huyi.demo.generate.GenerateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

@SpringBootApplication
public class MybatisgenerateApplication {



    public static void main(String[] args) {

        SpringApplication.run(MybatisgenerateApplication.class, args);
    }

}
