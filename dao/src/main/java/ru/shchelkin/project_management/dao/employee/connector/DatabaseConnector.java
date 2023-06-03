package ru.shchelkin.project_management.dao.employee.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String HOST = "localhost";
    private static final String PORT = "5432";
    private static final String DATABASE_NAME = "project_management";

    private static final String DATABASE_URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE_NAME;

    private static final  String USER = "postgres";
    private static final  String PASSWORD = "rootroot";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
