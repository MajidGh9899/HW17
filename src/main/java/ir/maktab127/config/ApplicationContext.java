package ir.maktab127.config;


import ir.maktab127.repositories.*;
import ir.maktab127.services.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class ApplicationContext {
    private static ApplicationContext instance;
    private Connection connection;

    private ApplicationContext() {}

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    ApplicationProperties.JDBC_URL,
                    ApplicationProperties.JDBC_USER,
                    ApplicationProperties.JDBC_PASSWORD
            );
        }
        return connection;
    }
}