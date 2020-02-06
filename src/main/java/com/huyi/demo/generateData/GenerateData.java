package com.huyi.demo.generateData;

import java.io.IOException;
import java.sql.SQLException;

public interface GenerateData {

    String assembly( String tableName, String queryTableSql) throws SQLException;

    /**
     * 生成数据
     * @param path  表名
     * @param data  需要生成的数据
     * @return
     */
     void generateDate(String data , String tableName) throws IOException;


}
