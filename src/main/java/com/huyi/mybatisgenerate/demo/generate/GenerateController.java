package com.huyi.mybatisgenerate.demo.generate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@RestController
public class GenerateController {

    @Autowired
    public GenerateService service;
    
    @RequestMapping("/generate")
    public String generateFile() throws SQLException, IOException, ClassNotFoundException {
        String   dataBas = service.executQuery();
        return  "生成"+dataBas+"数据库下表成功！";
    }
}
