package org.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Employee;

import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/DeleteEmployeeServlet")
public class DeleteEmployeeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get employee ID from the form input and ensure it's a valid integer
        String empIdStr = request.getParameter("emp_id");
        if (empIdStr != null && !empIdStr.isEmpty()) {
            try {
                int employeeId = Integer.parseInt(empIdStr);  // Parse to integer

                // Perform deletion logic
                boolean isDeleted = deleteEmployeeFromDatabase(employeeId);

                if (isDeleted) {
                    // Redirect back to employee list after successful deletion
                    response.sendRedirect("displayEmployee");  // Replace with your actual employee listing page
                } else {
                    // Show error if deletion failed
                    response.getWriter().write("Error deleting employee.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("Invalid employee ID format.");
            }
        } else {
            response.getWriter().write("Employee ID is missing.");
        }
    }

    private boolean deleteEmployeeFromDatabase(int employeeId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isDeleted = false;

        try {
            // Establish the connection
            conn = DatabaseConnection.connectToDatabase();

            // SQL query to delete employee based on emp_id
            String sql = "DELETE FROM employee WHERE emp_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employeeId); // Set the employee ID to delete (as integer)

            // Execute the query
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                isDeleted = true; // Employee deleted successfully
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
