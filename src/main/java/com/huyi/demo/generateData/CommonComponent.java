package com.huyi.demo.generateData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;


@Component
public class CommonComponent {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private GenerateData generateData;

    public CommonComponent setGenerateData(GenerateData generateData) {
        this.generateData = generateData;
        return this;
    }

    public  String assembly(String tableName, String queryTableSql) throws SQLException {
        logger.info("开始组装");
        String assembly = generateData.assembly(tableName, queryTableSql);
        logger.info("组装成功");
        return assembly;
    }

    public   void generateDate(String data , String tableName) throws IOException {
        logger.info("开始生成");
        generateData.generateDate(data, tableName);
        logger.info("生成成功");
    }
}
