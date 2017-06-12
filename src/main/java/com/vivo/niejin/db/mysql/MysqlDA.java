package com.vivo.niejin.db.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vivo.niejin.db.model.ColumnModel;
import com.vivo.niejin.db.model.TableModel;
import com.vivo.niejin.db.utils.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MysqlDA {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 获得表列表
     * 
     * @param cmd
     * @return
     */
    public List<String> getAllTableNames(DBManager cmd) {

        List<String> tableNames = new ArrayList<String>();
        Connection conn = null;
        try {

            conn = cmd.getCon();
            DatabaseMetaData metaData = conn.getMetaData();
            String dbName = conn.getCatalog();

            ResultSet rs = metaData.getTables(dbName, null, null, new String[] { "TABLE" });
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tableNames.add(tableName);
            }

        } catch (SQLException e) {
            log.error("获得表列表失败", ". ", e.getLocalizedMessage());
        } finally {
            cmd = null;
        }
        return tableNames;
    }

    public TableModel getTableModel(DBManager cmd, String tableName) {
        StringBuffer sb = new StringBuffer();
        // sb.append("describe ").append(tableName);
        sb.append("SHOW FULL FIELDS FROM ").append(tableName);
        TableModel model = new TableModel();

        model.setTableName(tableName);
        List<ColumnModel> columns = new ArrayList<ColumnModel>();

        try {
            ResultSet rs = cmd.executeQuery(sb.toString());
            while (rs.next()) {
                ColumnModel e = new ColumnModel();
                String fieldName = rs.getString("Field");
                e.setColumnName(fieldName);
                e.setAutoIncrement(rs.getString("Extra").equalsIgnoreCase("auto_increment") ? true : false);
                e.setCanNull(rs.getString("Null").equalsIgnoreCase("YES") ? true : false);
                e.setColumnType(rs.getString("Type"));
                e.setDefaultValue(rs.getString("Default"));
                e.setPrimaryKey(rs.getString("Key").equalsIgnoreCase("PRI") ? true : false);
                e.setHasIndex(rs.getString("Key").equalsIgnoreCase("MUL") ? true : false);
                // 增加列的comment信息
                e.setComment(rs.getString("comment"));
                columns.add(e);
            }
            model.setColumns(columns);
        } catch (SQLException e) {
            log.error("获得表列表失败", ". ", e.getLocalizedMessage());
        } finally {
           cmd = null;
        }
        return model;
    }
}
