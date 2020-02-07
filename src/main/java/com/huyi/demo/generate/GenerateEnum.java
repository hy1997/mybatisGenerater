package com.huyi.demo.generate;

public enum GenerateEnum {
    //mysql 查询库下面所有的表
    TABLES("01"),
    //mysql 查询库下面所有的表 的字段
    COLUMN ("O2"),
    //数据库类型
    DATATYPE_MYSQL ("MySQL"),
    //数据库类型
    DATATYPE_ORACLE ("Oracle");

    // 成员变量
    private String args;

    GenerateEnum(String args) {
        this.args=args;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
}
