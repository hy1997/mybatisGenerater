package com.huyi.demo.generate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;


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
