package com.vivo.niejin.db.model;

import com.vivo.niejin.db.utils.TableConvertUtil;

/**
 * Created by NieJin on 2017/6/8.
 */
public class ColumnModel {

    private boolean isPrimaryKey;

    private String columnName;
    private String columnType;

    private Integer columnLength;
    private String defaultValue;

    private boolean isAutoIncrement;
    private boolean canNull;
    private boolean hasIndex;

    private String comment;

    private String javaMethodName;
    private String javaFieldName;
    private String javaTypeNameStr;
    private String javaTypeMethodStr;

    private String myBatisTypeName;

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        this.setJavaFieldName(columnName);
        this.setJavaMethodName(columnName);
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        int index = columnType.indexOf('(');
        if (index >= 0) {
            this.columnType = columnType.substring(0, index);
            String length = columnType.substring(index + 1,
                    columnType.lastIndexOf(')'));
            if (length.indexOf(',') >= 0) {
                length = length.substring(0, length.indexOf(','));
            }

            int iLength = 10;
            try {
                iLength = new Integer(length);
            } catch (NumberFormatException e) {
                // e.printStackTrace();
            }

            this.columnLength = iLength;
        } else {
            this.columnType = columnType;
        }

        this.setJavaTypeMethodStr(this.getColumnType());
        this.setJavaTypeNameStr(this.getColumnType());
        this.setMyBatisTypeName(this.getColumnType());
    }

    public Integer getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(Integer columnLength) {
        this.columnLength = columnLength;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    public boolean isCanNull() {
        return canNull;
    }

    public void setCanNull(boolean canNull) {
        this.canNull = canNull;
    }

    public boolean isHasIndex() {
        return hasIndex;
    }

    public void setHasIndex(boolean hasIndex) {
        this.hasIndex = hasIndex;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJavaMethodName() {
        return javaMethodName;
    }

    private void setJavaMethodName(String javaMethodName) {
        this.javaMethodName = TableConvertUtil.columnNameToJavaMethodName(javaMethodName);
    }

    public String getJavaFieldName() {
        return javaFieldName;
    }

    private void setJavaFieldName(String javaFieldName) {
        this.javaFieldName = TableConvertUtil.columnNameToJavaFieldName(javaFieldName);
    }

    public String getJavaTypeNameStr() {
        return javaTypeNameStr;
    }

    private void setJavaTypeNameStr(String javaTypeNameStr) {
        this.javaTypeNameStr = TableConvertUtil.mySqlTypeConvertJavaType(javaTypeNameStr);
    }

    public String getJavaTypeMethodStr() {
        return javaTypeMethodStr;
    }

    private void setJavaTypeMethodStr(String javaTypeMethodStr) {
        this.javaTypeMethodStr = TableConvertUtil.mySqlTypeConvertJavaType(javaTypeMethodStr);;
    }

    public String getMyBatisTypeName() {
        return myBatisTypeName;
    }

    public void setMyBatisTypeName(String columnType) {
        this.myBatisTypeName = TableConvertUtil.mySqlTypeCOnvertMyBatisType(columnType);
    }
}
