package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:collabtask.db";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String createUsers = "CREATE TABLE IF NOT EXISTS Users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT,"
                    + "email TEXT UNIQUE,"
                    + "password TEXT,"
                    + "role TEXT"
                    + ");";
            stmt.execute(createUsers);

            String createTasks = "CREATE TABLE IF NOT EXISTS Tasks ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "title TEXT,"
                    + "description TEXT,"
                    + "status TEXT,"
                    + "assignee TEXT,"
                    + "deadline TEXT"
                    + ");";
            stmt.execute(createTasks);
            
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}
