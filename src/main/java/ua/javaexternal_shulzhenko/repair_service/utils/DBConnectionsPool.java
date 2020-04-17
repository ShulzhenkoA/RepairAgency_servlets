package ua.javaexternal_shulzhenko.repair_service.utils;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnectionsPool {

    private static BasicDataSource connectionsPool = new BasicDataSource();
    static {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("dbresources");
        connectionsPool.setUrl(resourceBundle.getString("url"));
        connectionsPool.setUsername(resourceBundle.getString("user"));
        connectionsPool.setPassword(resourceBundle.getString("password"));
        connectionsPool.setInitialSize(Integer.parseInt(resourceBundle.getString("initialSize")));
        connectionsPool.setMaxTotal(Integer.parseInt(resourceBundle.getString("totalSize")));
    }

    private DBConnectionsPool() {
    }

    public static Connection getConnection() throws SQLException {
        return connectionsPool.getConnection();
    }

    public static BasicDataSource getConnectionsPool() {
        return connectionsPool;
    }
}
