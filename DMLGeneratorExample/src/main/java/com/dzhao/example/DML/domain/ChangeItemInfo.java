package com.dzhao.example.DML.domain;

import com.dzhao.example.DML.annotation.QueryInfo;

@QueryInfo(nativeQueryFrom = "query.xml")
public class ChangeItemInfo {
    private String tableName;
    private String columnName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

}
