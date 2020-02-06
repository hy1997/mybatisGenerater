package com.huyi.demo.generate;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.*;
import java.util.List;

@Service
public class GenerateService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Connection conn;

    @Autowired
    Assemble assemble;

    public String executQuery() throws IOException {
        ResultSet resultSet = null;
        String dataBas = null;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            String dataBaseType = metaData.getDatabaseProductName();
            logger.info("连接的数据类型为：" + dataBaseType);
            dataBas = conn.getCatalog();
            logger.info("连接的数据库名称为：" + dataBas );
            String sql = GenerateUtils.select(dataBaseType, GenerateConstant.TABLES, dataBas);
            logger.info("获取到查询数据库下所有表SQL：" + sql );
            resultSet = GenerateUtils.getResultSet(conn, sql);
            logger.info("获取到结果集：" + resultSet );
            assemble.generateJvavaClass(dataBaseType, resultSet, dataBas);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dataBas;
    }
}
