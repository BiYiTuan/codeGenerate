package com.vivo.niejin.db.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Created by NieJin on 2017/6/8.
 */
public class TableConvertUtil {

    public static String tableNameToJavaName(String tableName) {
        String camelName = tableNameToJavaMethodName(tableName);
        return (camelName.substring(0, 1).toUpperCase() + camelName.substring(1)).trim();
    }

    public static String tableNameToJavaMethodName(String tableName) {
        if (tableName.startsWith("t_")) {
            tableName = tableName.substring(2);
        }

        if (tableName.startsWith("tb_")) {
            tableName = tableName.substring(3);
        }

        return camelize(tableName).trim();

    }

    public static String columnNameToJavaFieldName(String columnName) {
        return camelize(columnName).trim();

    }

    public static String columnNameToJavaMethodName(String columnName) {
        String camelName = camelize(columnName);
        return (camelName.substring(0, 1).toUpperCase() + camelName.substring(1)).trim();
    }

    /**
     * /**
     * 将以"_"分割的字符串转为驼峰命名的字符串
     *
     * @param name
     */
    public static String camelize(String name) {
        String[] ss = name.split("_");
        String dest = "";
        if (ss.length > 1) {
            for (String tmp : ss) {
                dest += tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
            }
        } else {
            dest = name;
        }

        if (StringUtils.isNotBlank(dest)) {
            dest = dest.substring(0, 1).toLowerCase() + dest.substring(1);
        }
        return dest.trim();
    }

    public static String mySqlTypeConvertJavaType(String mysqlType) {
        if (mysqlType.equalsIgnoreCase("Integer") ||
                mysqlType.equalsIgnoreCase("int")) {
            return "Integer";
        } else if (mysqlType.equalsIgnoreCase("TIMESTAMP")) {
            return "Date";
        } else if (mysqlType.equalsIgnoreCase("DATETIME")) {
            return "Date";
        } else if (mysqlType.equalsIgnoreCase("TINYINT")) {
            return "Integer";
        } else if (mysqlType.equalsIgnoreCase("VARCHAR")) {
            return "String";
        } else if (mysqlType.equalsIgnoreCase("BIGINT")) {
            return "Long";
        } else if (mysqlType.equalsIgnoreCase("DATE")) {
            return "Date";
        } else if (mysqlType.equalsIgnoreCase("TIME")) {
            return "Time";
        } else if (mysqlType.equalsIgnoreCase("SMALLINT")) {
            return "Integer";
        } else if (mysqlType.equalsIgnoreCase("decimal")) {
            return "BigDecimal";
        } else if (mysqlType.equalsIgnoreCase("DOUBLE")) {
            return "Double";
        } else if (mysqlType.equalsIgnoreCase("FLOAT")) {
            return "Float";
        } else if (mysqlType.equalsIgnoreCase("MEDIUMINT")) {
            return "Integer";
        } else if (mysqlType.equalsIgnoreCase("CHAR")) {
            return "Integer";
        } else {
            return "String";
        }
    }

    public static String mySqlTypeCOnvertMyBatisType(String mysqlType) {
        if (mysqlType.equalsIgnoreCase("Integer") ||
                mysqlType.equalsIgnoreCase("int")) {
            return "INTEGER";
        } else if (mysqlType.equalsIgnoreCase("TIMESTAMP")) {
            return "TIMESTAMP";
        } else if (mysqlType.equalsIgnoreCase("DATETIME")) {
            return "DATE";
        } else if (mysqlType.equalsIgnoreCase("TINYINT")) {
            return "TINYINT";
        } else if (mysqlType.equalsIgnoreCase("VARCHAR")) {
            return "VARCHAR";
        } else if (mysqlType.equalsIgnoreCase("BIGINT")) {
            return "BIGINT";
        } else if (mysqlType.equalsIgnoreCase("DATE")) {
            return "DATE";
        } else if (mysqlType.equalsIgnoreCase("TIME")) {
            return "TIME";
        } else if (mysqlType.equalsIgnoreCase("SMALLINT")) {
            return "SMALLINT";
        } else if (mysqlType.equalsIgnoreCase("decimal")) {
            return "DECIMAL";
        } else if (mysqlType.equalsIgnoreCase("DOUBLE")) {
            return "DOUBLE";
        } else if (mysqlType.equalsIgnoreCase("FLOAT")) {
            return "FLOAT";
        } else if (mysqlType.equalsIgnoreCase("MEDIUMINT")) {
            return "INTEGER";
        } else if (mysqlType.equalsIgnoreCase("CHAR")) {
            return "CHAR";
        } else {
            return "NULL";
        }
    }
}
