package org.example.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig(maxFileSize = 16177215) // Max file size ~16MB
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); // Get username from form
        InputStream inputStream = null; // Stream for file data
        Connection conn = null;
        PreparedStatement stmt = null;

        // Debugging output
        System.out.println("Received username: " + username);

        // Get uploaded file
        Part filePart = request.getPart("picture");
        if (filePart == null || filePart.getSize() == 0) {
            response.getWriter().println("Error: No file uploaded or file is empty.");
            return; // Exit if no file is uploaded
        }

        inputStream = filePart.getInputStream(); // Convert file to InputStream
        System.out.println("Uploaded file name: " + filePart.getSubmittedFileName());

        // Database credentials
        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String pass = "Rufus@1122";

        try {
            // Load PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL driver loaded successfully.");

            // Connect to database
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Database connection established.");

            // SQL query to update picture in database
            String sql = "UPDATE users SET profile_image = ? WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            System.out.println("Prepared statement created: " + sql);

            // Set parameters
            stmt.setBinaryStream(1, inputStream, (int) filePart.getSize());
            stmt.setString(2, username);
            System.out.println("Username parameter set for prepared statement: " + username);

            // Execute update
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Execute update called. Rows updated: " + rowsUpdated);

            if (rowsUpdated > 0) {
                response.getWriter().println("Picture uploaded successfully!");
            } else {
                response.getWriter().println("Error: User not found.");
            }
        } catch (ClassNotFoundException e) {
            response.getWriter().println("Error: PostgreSQL driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            response.getWriter().println("Error: Database error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            if (stmt != null) {
                try {
                    stmt.close();
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
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Database connection closed.");
        }
    }
}