package com.vivo.niejin.db.model;

import com.vivo.niejin.db.utils.TableConvertUtil;

import java.util.List;

/**
 * Created by NieJin on 2017/6/8.
 */
public class TableModel {

    private String tableName;
    private String comment;
    private String dbName;

    private Integer columnCount;

    private ColumnModel primaryKeyColumn;

    private String javaName;
    private String javaMethodName;

    private List<ColumnModel> columns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        this.setJavaMethodName(TableConvertUtil.tableNameToJavaMethodName(tableName));
        this.setJavaName(TableConvertUtil.tableNameToJavaName(tableName));
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Integer getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }

    public ColumnModel getPrimaryKeyColumn() {
        return primaryKeyColumn;
    }

    public void setPrimaryKeyColumn(ColumnModel primaryKeyColumn) {
        this.primaryKeyColumn = primaryKeyColumn;
    }

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public String getJavaMethodName() {
        return javaMethodName;
    }

    public void setJavaMethodName(String javaMethodName) {
        this.javaMethodName = javaMethodName;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
       this.columns = columns;
       for(ColumnModel tmp : columns){
           if(tmp.isPrimaryKey()){
               this.primaryKeyColumn = tmp;
               columns.remove(tmp);
               break;
           }
       }
    }
}
