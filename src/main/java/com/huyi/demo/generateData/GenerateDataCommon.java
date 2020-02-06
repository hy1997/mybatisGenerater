package com.huyi.demo.generateData;

import com.huyi.demo.Utils.StringUtils;
import com.huyi.demo.generate.GenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;

public abstract class GenerateDataCommon implements GenerateData {

    @Autowired
    Connection conn;

    @Value("${myProps.path}")
    String path;

    @Value("${myProps.interfacePath}")
    String interfacePath;

    /**
     * 生成文件
     * @param data  需要生成的数据
     * @param tableName 表名
     * @throws IOException
     */
    @Override
    public void generateDate(String data, String tableName) throws IOException {

        if (StringUtils.isNotEmpty(path) || StringUtils.isNotEmpty(interfacePath)) {
            if (this instanceof GenerateJavaFile) {
                //获取到得到工程的路径
                String filePath = System.getProperty("user.dir") + path;
                this.mkdir(filePath);
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath + GenerateUtils.toTable(tableName) + ".java"));
                bw.write(data);
                bw.close();
            } else {
                //获取到得到工程的路径
                String filePath = System.getProperty("user.dir") + interfacePath;
                this.mkdir(filePath);
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath + GenerateUtils.toTable(tableName) + "Mapper.java"));
                bw.write(data);
                bw.close();
            }
        } else {
            new Exception("需要生成的Java文件路径不存在！");
        }
    }

    /**
     * 新建文件夹
     * @param filePath  文件夹路径
     */
    public void mkdir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {//如果文件夹不存在
            boolean mkdir = file.mkdirs();//创建文件夹
        }
    }
}
