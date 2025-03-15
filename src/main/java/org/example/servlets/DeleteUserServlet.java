package org.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User; // Import the User class
import org.example.servlets.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/DeleteUserServlet") // Updated URL mapping
public class DeleteUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user ID from the form input and ensure it's a valid integer
        String userIdStr = request.getParameter("user_id"); // Updated parameter name
        if (userIdStr != null && !userIdStr.isEmpty()) {
            try {
                int userId = Integer.parseInt(userIdStr);  // Parse to integer

                // Perform deletion logic
                boolean isDeleted = deleteUserFromDatabase(userId);

                if (isDeleted) {
                    // Redirect back to user list after successful deletion
                    response.sendRedirect("displayUser");  // Redirect to the user listing page
                } else {
                    // Show error if deletion failed
                    response.getWriter().write("Error deleting user.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("Invalid user ID format.");
            }
        } else {
            response.getWriter().write("User ID is missing.");
        }
    }

    private boolean deleteUserFromDatabase(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isDeleted = false;

        try {
            // Establish the connection
            conn = DatabaseConnection.connectToDatabase();

            // SQL query to delete user based on user_id
            String sql = "DELETE FROM users WHERE id = ?"; // Updated table and column name
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId); // Set the user ID to delete (as integer)

            // Execute the query
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                isDeleted = true; // User deleted successfully
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection and statement
            DatabaseConnection.closeConnection(conn);
        }

        return isDeleted;
    }
}