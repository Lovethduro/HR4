package org.example.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ PostgreSQL Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    public static Connection connectToDatabase() {
        // Replace with your PostgreSQL database details
        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String password = "Rufus@1122";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("✅ Successfully connected to PostgreSQL database!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error connecting to database!");
            e.printStackTrace();
        }
        return conn; // Return the connection
    }
}
