package com.huyi.demo.generateData;

import com.huyi.demo.Utils.StringUtils;
import com.huyi.demo.generate.GenerateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenerateJavaFile extends GenerateDataCommon {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${myProps.path}")
    String path;

    @Value("${myProps.memoField}")
    Boolean  memoField;

    @Override
    public String assembly( String tableName, String queryTableSql) throws SQLException {
        StringBuffer stringBuffer = new StringBuffer();
//        截取路劲用做Java文件的package 路径
        String subPath = GenerateUtils.subPath(path, "com");
//        执行sql
        ResultSet resultSet = GenerateUtils.getResultSet(conn, queryTableSql);
        logger.info("生成的路径：+" + subPath + "！");
        stringBuffer.append("package  " + subPath.toString() + "\r\n" + "\r\n" + "\r\n");
        stringBuffer.append("public  class " + GenerateUtils.toTable(tableName) + "{");
        while (resultSet.next()) {
//            是否需要字段备注
            if(memoField){
                stringBuffer.append("\r\n" + "/**" + "\r\n" + "*" + resultSet.getString("COMMENTS") + "\r\n" + "*/");
            }
            stringBuffer.append("\r\n" + "private " + GenerateUtils.toTypeByName(resultSet.getString("DATA_TYPE")) + "    " + GenerateUtils.toUpper(resultSet.getString("COLUMN_NAME")) + ";");
        }
        stringBuffer.append("\r\n" + "}");
        logger.info("获取+" + tableName + "表数据成功");
        return stringBuffer.toString();

    }

    @Override
    public void generateDate(String data, String tableName) throws IOException {
        //获取到得到工程的路径
        if(StringUtils.isNotEmpty(path)){
            String filePath = System.getProperty("user.dir") + path;
            File file = new File(filePath);
            if (!file.exists()) {//如果文件夹不存在
                boolean mkdir = file.mkdirs();//创建文件夹
                System.out.println(mkdir);
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath + GenerateUtils.toTable(tableName) + ".java"));
            bw.write(data);
            bw.close();
        }else{
            new Exception("需要生成的Java文件路径不存在！");
        }

    }
}
