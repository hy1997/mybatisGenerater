package com.huyi.demo.generateData;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;

public abstract  class GenerateDataCommon implements GenerateData {
    @Autowired
    Connection conn;

}
