package com.huyi.demo.generateData;

import com.huyi.demo.generate.GenerateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GenerateJavaInterface extends GenerateDataCommon {
    private Logger logger = LoggerFactory.getLogger(this.getClass());



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
        logger.info("生成的路径：+" + subPath + "");
        stringBuilder.append("package  " + subPath.toString() + "\r\n" + "\r\n" + "\r\n");
        stringBuilder.append("public  interface " + GenerateUtils.toTable(tableName) + "Mapper{");
        stringBuilder.append("int deleteByPrimaryKey(Integer id);");
        stringBuilder.append("int insertSelective("+GenerateUtils.toTable(tableName)+" record);");
        stringBuilder.append(GenerateUtils.toTable(tableName)+"   selectByPrimaryKey(Integer id);");
        stringBuilder.append("int updateByPrimaryKeySelective("+GenerateUtils.toTable(tableName) +" record);");
        stringBuilder.append("int updateByPrimaryKey("+GenerateUtils.toTable(tableName) +" record);");
        stringBuilder.append("\r\n" + "}");
        logger.info("获取+" + tableName + "表数据成功 ");
        return stringBuilder.toString();
    }


}
