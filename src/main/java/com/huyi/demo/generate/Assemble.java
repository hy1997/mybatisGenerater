package com.huyi.demo.generate;

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
import java.util.List;

@Component
public class Assemble {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Connection conn;

    @Value("${generate.path}")
    String path;

    /**
     * @param dataBaseType 数据库类型
     * @param resultSet    查询结果集
     * @param dataBas      数据库名称
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public void generateJvavaClass(String dataBaseType, ResultSet resultSet, String dataBas) throws SQLException, IOException {
        logger.info("===============================================开始生成：" + dataBas + "数据库下表数据！===============================================");
        StringBuffer stringBuffer = null;
            try {
                String subPath = GenerateUtils.subPath(path, "com");
                while (resultSet.next()) {
                    // 获取到 resultSet 结果集下的表名
                    String tableName = resultSet.getString("TABLE_NAME");
                    //获取到查询数据库下所有表 sql
                    String queryTableSql = GenerateUtils.select(dataBaseType, GenerateConstant.COLUMN, tableName, dataBas);
                    String tableData = getTaleDataByTableName(subPath, tableName, queryTableSql);
                    readJaveFile(tableData, GenerateUtils.toUpper(tableName));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


//    public static StringBuffer generaterJvavaBean(List<ResultSet> rSets) throws SQLException {
//        StringBuffer buffer = new StringBuffer();
//        ResultSet rSet = rSets.get(0);
//        ResultSet rSet1 = rSets.get(1);
//        while (rSet.next()) {
//            buffer.append("public " + GenerateUtils.toTypeByName(rSet1, rSet.getString("COLUMN_NAME")) + GenerateUtils.toUpper(rSet.getString("COLUMN_NAME")) + ";     //" + rSet.getString("COMMENTS"));
//            buffer.append("\r\n");
//        }
//        return buffer;
//    }

    /**
     * @param buffer    生成的表文件
     * @param tableName tableName 表名称
     * @throws IOException
     */
    public void readJaveFile(String buffer, String tableName) throws IOException {
        //获取到得到工程的路径
        String filePath = System.getProperty("user.dir")+path;
        File file = new File(filePath);
        if (!file.exists()) {//如果文件夹不存在
            boolean mkdir = file.mkdirs();//创建文件夹
            System.out.println(mkdir);
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath+ tableName + ".java"));
        bw.write(buffer);
        bw.close();
        logger.info("===============================================+" + tableName + "+。Java代码生成成功===============================================！");
    }

    /**
     * @param subPath       生成的路径
     * @param tableName     生成的表名
     * @param queryTableSql 查询表数据的sql
     * @return
     * @throws SQLException
     */
    public String getTaleDataByTableName(String subPath, String tableName, String queryTableSql) throws SQLException {
        StringBuffer stringBuffer = new StringBuffer();
        //执行sql
        ResultSet resultSet = GenerateUtils.getResultSet(conn, queryTableSql);
        logger.info("===============================================生成的路径：+" + subPath + "===============================================！");
        stringBuffer.append("package  " + subPath.toString() + "\r\n" + "\r\n" + "\r\n");
        stringBuffer.append("public  class " + GenerateUtils.toUpper(tableName) + "{");
        while (resultSet.next()) {
            stringBuffer.append("\r\n" + "/**" + "\r\n" + "*" + resultSet.getString("COMMENTS") + "\r\n" + "*/");
            stringBuffer.append("\r\n" + "private " + GenerateUtils.toTypeByName(resultSet.getString("DATA_TYPE")) + "    " + GenerateUtils.toUpper(resultSet.getString("COLUMN_NAME")) + ";");
        }
        stringBuffer.append("\r\n" + "}");
        logger.info("===============================================获取+" + tableName + "表数据成功 ===============================================！");
        return stringBuffer.toString();
    }
}
