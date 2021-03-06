package com.huyi.demo.generate;

import com.huyi.demo.Utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerateUtils {
    /**
     * 生成字段名称 首字母不用大写
     *
     * @param str
     * @return
     */
    public static String toUpper(String str) {
        if (str != null) {
            StringBuffer buffer = new StringBuffer();
            String str1 = str.toLowerCase();
            if (str1.contains("_")) {// 包含下划线
                String[] String4 = str1.split("_");
                buffer.append(String4[0]);
                for (int j = 1; j < String4.length; j++) {
                    // 首字母变大写
                    buffer.append(String4[j].substring(0, 1).toUpperCase() + String4[j].substring(1, String4[j].length()));
                }
                return buffer.toString();
            } else {
                return str1;
            }
        }
        return str;
    }

    /**
     * 生成类名称
     *
     * @param str
     * @return
     */
    public static String toTable(String tableName) {
        if (tableName != null) {
            StringBuffer buffer = new StringBuffer();
//          先全部变小写
            String lowerCaseTableName = tableName.toLowerCase();
            if (lowerCaseTableName.contains("_")) {// 包含下划线
                String[] subTables = lowerCaseTableName.split("_");
                for (String subTable : subTables) {
                    // 首字母变大写
                    buffer.append(subTable.substring(0, 1).toUpperCase() + subTable.substring(1, subTable.length()));
                }
                return buffer.toString();
            } else {
                return lowerCaseTableName;
            }
        }
        return tableName;
    }


    public static String toTypeByName(ResultSet rSet, String name) throws SQLException {
        while (rSet.next()) {
            String columnName = rSet.getString("COLUMN_NAME").trim();
            String dataType = rSet.getString("DATA_TYPE");
            if (columnName.equals(name.trim())) {
                switch (dataType) {
                    case "VARCHAR2":
                        return "String";
                    case "NUMBER":
                        return "BigDecimal";
                    case "CHAR":
                        return "String";
                    default:
                        return "String";
                }
            }

        }
        return name;

    }

    /**
     * 根据数据库类型转换成 Java基本类型
     * @param dataType
     * @return
     * @throws SQLException
     */
    public static String toTypeByName(String dataType) throws SQLException {
        switch (dataType) {
            case "VARCHAR2":
                return "String";
            case "NUMBER":
                return "BigDecimal";
            case "CHAR":
                return "String";
            default:
                return "String";
        }
    }

    /**
     * @param dataBaseType 数据库类型
     * @param str          sql编号
     * @param tableName    表明
     * @param schema       数据库名称
     * @return
     * @throws SQLException
     */
    public static String select(String dataBaseType, String str, String tableName, String schema) throws SQLException {
        //mysql
        if (GenerateEnum.DATATYPE_MYSQL.getArgs().equals(dataBaseType)) {
            if (GenerateEnum.TABLES.getArgs().equals(str)) {
                //根据schema 查出schema下所有表的信息
                return "select TABLE_NAME from INFORMATION_SCHEMA.Columns where table_schema='" + schema + "'   group by TABLE_NAME";
            } else if (GenerateEnum.COLUMN.getArgs().equals(str)) {
                //mysql 查询库下面所有的表 的字段
                return "select TABLE_NAME,COLUMN_NAME,COLUMN_COMMENT AS COMMENTS ,DATA_TYPE from INFORMATION_SCHEMA.Columns where table_schema='" + schema + "'  AND  table_name ='" + tableName + "'";
            }
            // oracle
        } else if (GenerateEnum.DATATYPE_ORACLE.getArgs().equals(dataBaseType)) {
            if (GenerateEnum.TABLES.getArgs().equals(str)) {
                return " select COLUMN_NAME,COMMENTS from  all_col_comments where  table_name='\" + table + \"'\";";
            } else if (GenerateEnum.COLUMN.getArgs().equals(str)) {
                return "select COLUMN_NAME,DATA_TYPE from  all_tab_columns where  table_name='\" + table + \"'\"";
            }
        }
        return null;
    }

    /**
     * @param dataBaseType 数据库类型
     * @param str          sql编号
     * @param schema       数据库名称
     * @return
     * @throws SQLException
     */
    public static String select(String dataBaseType, String str, String schema) throws SQLException {
        return select(dataBaseType, str, null, schema);
    }


    public static ResultSet getResultSet(Connection conn, String sql) throws SQLException {
        PreparedStatement prepareStatement = conn.prepareStatement(sql);
        ResultSet rSet = prepareStatement.executeQuery(sql);
        return rSet;
    }

    /**
     * @param path 路劲
     * @return
     */
    public static String subPath(String path, String indexPath) {

        if (StringUtils.isEmpty(indexPath)) {
            new Exception("需要截取路径为空");
        } else {
            String[] address = path.split("/");
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < address.length; i++) {
                if (indexPath.equals(address[i])) {
                    for (int j = 0; j < address.length - 1; i++) {
                        j = i;
                        if (j == address.length - 1) {
                            stringBuffer.append(address[i] + ";");
                        } else {
                            stringBuffer.append(address[i] + ".");
                        }

                    }
                }
            }
            return stringBuffer.toString();

        }
        return null;
    }


}
