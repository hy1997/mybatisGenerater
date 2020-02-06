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

@Component
public class GenerateJavaInterface extends GenerateDataCommon {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${myProps.interfacePath}")
    String interfacePath;



    @Override
    public String assembly( String tableName, String queryTableSql) {
        //        截取路劲用做Java文件的package 路径
        String subPath = GenerateUtils.subPath(interfacePath, "com");
        StringBuilder stringBuilder = new StringBuilder();
        /**
         * int deleteByPrimaryKey(Integer id);
         *
         *     int insert(user record);
         *
         *
         *     int insertSelective(user record);
         *
         *     user selectByPrimaryKey(Integer id);
         *
         *     int updateByPrimaryKeySelective(user record);
         *
         *     int updateByPrimaryKey(user record);
         */
        logger.info("===============================================生成的路径：+" + subPath + "===============================================！");
        stringBuilder.append("package  " + subPath.toString() + "\r\n" + "\r\n" + "\r\n");
        stringBuilder.append("public  interface " + GenerateUtils.toTable(tableName) + "Mapper{");
        stringBuilder.append("int deleteByPrimaryKey(Integer id);");
        stringBuilder.append("int insertSelective("+GenerateUtils.toTable(tableName)+" record);");
        stringBuilder.append(GenerateUtils.toTable(tableName)+"   selectByPrimaryKey(Integer id);");
        stringBuilder.append("int updateByPrimaryKeySelective("+GenerateUtils.toTable(tableName) +" record);");
        stringBuilder.append("int updateByPrimaryKey("+GenerateUtils.toTable(tableName) +" record);");
        stringBuilder.append("\r\n" + "}");

        logger.info("===============================================获取+" + tableName + "表数据成功 ===============================================！");
        return stringBuilder.toString();
    }

    @Override
    public void generateDate(String data, String tableName) throws IOException {
        if(StringUtils.isNotEmpty(interfacePath)){
            String filePath = System.getProperty("user.dir") + interfacePath;
            File file = new File(filePath);
            if (!file.exists()) {//如果文件夹不存在
                boolean mkdir = file.mkdirs();//创建文件夹
                System.out.println(mkdir);
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath + GenerateUtils.toTable(tableName) + "Mapper.java"));
            bw.write(data);
            bw.close();
        }else{
            new Exception("需要生成的Java文件路径不存在！");
        }
    }
}
