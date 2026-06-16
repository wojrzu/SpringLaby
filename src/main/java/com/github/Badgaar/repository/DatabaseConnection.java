package com.github.Badgaar.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection get() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = System.getenv("DB_URL");
                String user = System.getenv("DB_USER");
                String password = System.getenv("DB_PASSWORD");

                if (url == null || url.isBlank()) {
                    throw new IllegalStateException("Brak enva");
                }

                if (user != null && password != null) {
                    connection = DriverManager.getConnection(url, user, password);
                } else {
                    connection = DriverManager.getConnection(url);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}