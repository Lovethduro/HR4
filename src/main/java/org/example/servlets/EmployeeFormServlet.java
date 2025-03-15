package org.example.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.example.servlets.DatabaseConnection;

@WebServlet("/createEmployee")
public class EmployeeFormServlet extends HttpServlet {

    // Handle GET request (show the employee creation form)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the employee form JSP
        request.getRequestDispatcher("/pages/employeeForm.jsp").forward(request, response);
    }

    // Handle POST request (form submission)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve form data (e.g., employee first name, last name, phone, etc.)
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String createdBy = request.getParameter("createdBy");
        String email = request.getParameter("email");
        String department = request.getParameter("department");
        String position = request.getParameter("position");
        String role = request.getParameter("role");
        String qualification = request.getParameter("qualification");

        // SQL query to insert employee data
        String sql = "INSERT INTO employee (first_name, last_name, phone, postal_address, email, department, position, role, qualification, created_by) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Attempt to establish the connection using DatabaseConnection
        Connection conn = DatabaseConnection.connectToDatabase();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the values in the prepared statement
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, phone);
                stmt.setString(4, address);
                stmt.setString(5, qualification);
                stmt.setString(6, email);
                stmt.setString(7, department);
                stmt.setString(8, position);
                stmt.setString(9, role);
                stmt.setString(10, createdBy);

                // Debugging line: Before executing the query
                System.out.println("Prepared statement is ready to execute, attempting to insert employee data...");

                // Execute the query
                int rowsAffected = stmt.executeUpdate();

                // Debugging line: After executing the query
                if (rowsAffected > 0) {
                    System.out.println("Employee data inserted successfully!");
                    response.sendRedirect("displayEmployee"); // Redirect to the employee list or success page
                } else {
                    // Handle failure to insert
                    System.out.println("Failed to create employee.");
                    response.getWriter().println("Failed to create employee.");
                }
            } catch (SQLException e) {
                // Handle any SQL exceptions (e.g., database operation issues)
                e.printStackTrace();
                response.getWriter().println("Error: " + e.getMessage());
            } finally {
                // Close the connection after the operation
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            // If the connection is null, handle the error
            System.out.println("Failed to connect to the database.");
            response.getWriter().println("Error: Unable to connect to the database.");
        }
    }
}
