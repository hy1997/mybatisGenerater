package com.huyi.demo.generate;

import com.huyi.demo.Utils.StringUtils;
import com.huyi.demo.generateData.CommonComponent;
import com.huyi.demo.generateData.GenerateJavaFile;
import com.huyi.demo.generateData.GenerateJavaInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

@Component
public class Assemble {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    GenerateJavaFile generateJavaFile;

    @Autowired
    CommonComponent comm;

    @Autowired
    GenerateJavaInterface generateJavaInterface;

    /**
     * @param dataBaseType 数据库类型
     * @param resultSet    查询结果集
     * @param dataBas      数据库名称
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public void generateJvavaClass(String dataBaseType, ResultSet resultSet, String dataBas) throws SQLException, IOException {
        logger.info("开始生成：" + dataBas + "数据库下表数据 接口！");
        StringBuffer stringBuffer = null;
        try {
            while (resultSet.next()) {
                // 获取到 resultSet 结果集下的表名
                String tableName = resultSet.getString("TABLE_NAME");
                //获取到查询数据库下所有表 sql
                String queryTableSql = GenerateUtils.select(dataBaseType, GenerateConstant.COLUMN, tableName, dataBas);
                //生成表
                comm.generateDate(comm.setGenerateData(generateJavaFile).assembly(tableName, queryTableSql),tableName);
                //生成的接口
                comm.generateDate(comm.setGenerateData(generateJavaInterface).assembly(tableName, queryTableSql),tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
