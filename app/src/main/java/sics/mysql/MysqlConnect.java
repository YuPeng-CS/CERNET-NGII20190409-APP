package sics.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnect {

    private final static String driver = "com.mysql.jdbc.Driver";// MySql驱动

    private final static String user = "root";// 用户名
    private final static String password = "123456";// 密码
    private final static String dbName = "scis";//数据库名

    //ipv4
    private final static String ip = "115:157:200:156";
    private final static String url = "jdbc:mysql://" + ip + ":3306/" + dbName + "?useSSL=false";
    private final static String batchUrl = url + "&useServerPrepStmts=false&rewriteBatchedStatements=true&useSSL=false";

    //ipv6
    private final static String ipv6="2001:250:4402:2002:115:157:200:156";
    private final static String ipv6Url="jdbc:mysql://address=(protocol=tcp)(host="+ipv6+")(port=3306)/"+dbName+"?useSSL=false";
    private final static String ipv6BatchUrl=ipv6Url+"&useServerPrepStmts=false&rewriteBatchedStatements=true";

    public static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(ipv6Url, user, password);
    }

    public static Connection getBatchConn() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(ipv6BatchUrl, user, password);
    }
}

