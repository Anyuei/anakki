package com.anakki.data.utils.datasourceUtil;

import com.alibaba.fastjson.JSONObject;
import com.anakki.data.bean.common.response.ColumnEntryResponse;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: MySQLUtil
 * Description:
 *
 * @author Anakki
 * @date 2023/5/31 2:13
 */
public class MySQLUtil {
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String TYPE_NAME = "TYPE_NAME";
    public static final String COLUMN_SIZE = "COLUMN_SIZE";
    public static final String REMARKS = "REMARKS";
    public static final String TABLE = "TABLE";

    public static ConcurrentHashMap<String, Connection> userMySQLConnections = new ConcurrentHashMap<>();

    public static void main(String[] args) throws SQLException {
        System.out.println(JSONObject.toJSONString(getTableColumnInfoList(getConnection(), "sx_data_type")));
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<JSONObject> getDatabaseNamesFromInstance(Long userId, String url, String user, String password) throws SQLException {
        Connection connection = getConnection(userId, url, user, password);
        return getDatabaseNamesFromInstance(connection);
    }

    /**
     * 获取数据库名称
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public static List<JSONObject> getDatabaseNamesFromInstance(Connection connection) throws SQLException {
        ResultSet query = query("SHOW DATABASES", connection);
        ArrayList<JSONObject> databaseNames = new ArrayList<>();
        // 执行SQL查询语句
//        ResultSet rs = stmt.executeQuery("SHOW DATABASES");
        if (null == query) {
            return databaseNames;
        }
        int columnCount = query.getMetaData().getColumnCount();
        // 遍历结果集，输出数据库名称
        while (query.next()) {
            JSONObject jsonObject = new JSONObject();
            for (int i = 1; i <=columnCount; i++) {
                String columnValue = query.getString(i);
                if (columnValue == null) {
                    columnValue = "";
                }
                jsonObject.put(query.getMetaData().getColumnName(i),columnValue);
            }
            databaseNames.add(jsonObject);
        }
        return databaseNames;
    }

//    public static List<JSONObject> getDatabaseInfo(Connection connection) throws SQLException {
//        ResultSet query = query("SELECT \n" +
//                "    @@version AS 'Database Version',\n" +
//                "    @@hostname AS 'Hostname',\n" +
//                "    @@port AS 'Port',\n" +
//                "    @@version_compile_os AS 'Operating System',\n" +
//                "    @@version_compile_machine AS 'Machine Architecture';", connection);
//        ArrayList<JSONObject> databaseNames = new ArrayList<>();
//        // 执行SQL查询语句
////        ResultSet rs = stmt.executeQuery("SHOW DATABASES");
//        if (null == query) {
//            return databaseNames;
//        }
//        int columnCount = query.getMetaData().getColumnCount();
//        // 遍历结果集，输出数据库名称
//        while (query.next()) {
//            JSONObject jsonObject = new JSONObject();
//            for (int i = 1; i <=columnCount; i++) {
//                String columnValue = query.getString(i);
//                if (columnValue == null) {
//                    columnValue = "";
//                }
//                jsonObject.put(query.getMetaData().getColumnName(i),columnValue);
//            }
//            databaseNames.add(jsonObject);
//        }
//        return databaseNames;
//    }

    /**
     * 获取表的DDL
     *
     * @param connection
     * @param tableName
     * @return
     * @throws SQLException
     */
    public static String getTableDDL(Connection connection, String tableName) throws SQLException {
        ResultSet query = query("show create table " + tableName, connection);
        if (null == query) {
            return "";
        }
        // 遍历结果集，输出数据库名称
        while (query.next()) {
            return (query.getString(2));
        }
        return "";
    }


    /**
     * 获取所有表字段信息以及表数据
     */
    public static List<JSONObject> getAllTableColumnAndData(Connection conn,String tableName,Long pageIndex,Long pageSize) {
        try {
            Long start = (pageIndex-1)*pageSize;  //算出起始下标
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from "+tableName+" limit "+start+" , "+pageSize);
            return getTableDataLinkedHashMap(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取所有表字段信息以及表数据
     */
    public static List<JSONObject> getAllTablesInfo(Connection conn,String databaseName,Long pageIndex,Long pageSize) {
        try {
            Long start = (pageIndex-1)*pageSize;  //算出起始下标
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from information_schema.tables where table_schema= '"+databaseName+"' limit "+start+" , "+pageSize);
            return getTableDataLinkedHashMap(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 获取表数据总数
     */
    public static Long getDataCount(Connection conn,String tableName) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from "+tableName);
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 获取表字段信息以及表数据
     */
    public static List<JSONObject> getTableColumnAndData(Connection conn,
                                                                   String queryDataSql) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(queryDataSql);
            return getTableDataLinkedHashMap(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取表数据
     */
    @SneakyThrows
    private static List<JSONObject> getTableDataLinkedHashMap(@NonNull ResultSet rs) {
        LinkedList<JSONObject> dataList = new LinkedList<>();
        int columnCount = rs.getMetaData().getColumnCount();
        // 插入数据
        while (rs.next()) {
            JSONObject jsonObject = new JSONObject();
            for (int i = 1; i <=columnCount; i++) {
                String columnValue = rs.getString(i);
                if (columnValue == null) {
                    columnValue = "";
                }
                jsonObject.put(rs.getMetaData().getColumnName(i),columnValue);
            }
            dataList.add(jsonObject);
        }
        return dataList;
    }

    /**
     * 获取某张表的字段信息
     */
    public static List<ColumnEntryResponse> getTableColumnInfoList(Connection conn, String tableName) {
        List<ColumnEntryResponse> columnList = new ArrayList<>();
        try {
            // 查询表的元数据信息
            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet resultSet = dbMetaData.getColumns(null, null, tableName, null);

            // 遍历结果集，将字段名添加到集合中
            while (resultSet.next()) {

                String columnName = resultSet.getString(COLUMN_NAME);
                String typeName = resultSet.getString(TYPE_NAME);
                String columnSize = resultSet.getString(COLUMN_SIZE);
                String remarks = resultSet.getString(REMARKS);
                ResultSet pkRs = dbMetaData.getPrimaryKeys(null, null, tableName);
                boolean isPrimaryKey = false;
                while (pkRs.next()) {
                    if (columnName.equals(pkRs.getString(COLUMN_NAME))) {
                        isPrimaryKey = true;
                        break;
                    }
                }
                columnList.add(new ColumnEntryResponse(columnName, typeName, columnSize, remarks, isPrimaryKey));
            }
            return columnList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getTablesFromInstanceAndDatabase(Long userId, String ip, String port, String databaseName, String user, String password) throws SQLException {
        Connection connection = getConnection(userId, ip, port, databaseName, user, password);
        return getTablesFromInstanceAndDatabase(connection,databaseName);
    }

    public static List<String> getTablesFromInstanceAndDatabase(Connection connection,String databaseName) throws SQLException {
        ArrayList<String> tableNames = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
        while (resultSet.next()) {
            tableNames.add(resultSet.getString("TABLE_NAME"));
        }
        return tableNames;
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://49.232.11.30:3306/", "root", "FengXiang_123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public synchronized static Connection getConnection(Long userId, String url, String user, String password) {
        Connection conn = null;
        try {
            if (userMySQLConnections.containsKey(userId.toString())) {
                Connection connection = userMySQLConnections.get(userId.toString());
                if (!connection.isClosed()) {
                    return userMySQLConnections.get(userId.toString());
                }
            }
            conn = DriverManager.getConnection(url, user, password);
            userMySQLConnections.put(userId.toString(), conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public synchronized static Connection getConnection(Long userId, String ip, String port, String database, String user, String password) {
        Connection conn = null;
        try {
            if (userMySQLConnections.containsKey(userId.toString())) {
                Connection connection = userMySQLConnections.get(userId.toString());
                if (!connection.isClosed()) {
                    return userMySQLConnections.get(userId.toString());
                }
            }
            conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/"+database, user, password);
            userMySQLConnections.put(userId.toString(), conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public synchronized static Connection getConnection(String userId) {
        Connection conn = null;
        try {
            if (userMySQLConnections.containsKey(userId)) {
                Connection connection = userMySQLConnections.get(userId);
                if (!connection.isClosed()) {
                    return userMySQLConnections.get(userId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 查询
    private static ResultSet query(String sql) {
        try {
            return getConnection().createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ResultSet query(String sql, Connection connection) {
        try {
            return connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 抽取通用的增删改操作
     *
     * @param
     * @return
     */
    public static int executeUpdateSDU(String sql, Object... objects) {
        int rows = -1;
        // 1.加载驱动
        // 2.得到数据库连接：Connection指向的接口
        Connection conn = null;
        // 3.得到Statement：接口指向的对象，使用Statement对象发送sql语句到数据库中执行，返回结果集
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            if (objects != null) {
                for (int i = 0; i < objects.length; i++) {
                    ps.setObject((i + 1), objects[i]);
                }
            }
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null, ps, conn);
        }
        return rows;
    }

    public static void closeAll(ResultSet rs, Statement state, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }
}
