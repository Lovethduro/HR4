package org.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Employee; // Import the Employee class

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/displayEmployee") // Define the URL pattern for the servlet
public class DisplayEmployeeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("search");
        if (searchQuery == null) {
            searchQuery = "";
        }

        List<Employee> employeeList = new ArrayList<>();
        Connection conn = DatabaseConnection.connectToDatabase(); // Initialize your Database connection class

        // Modify the query to search multiple fields
        String sql = "SELECT emp_id, first_name, last_name, postal_address, created_by, department, position, role, qualification " +
                "FROM employee " +
                "WHERE first_name ILIKE ? OR last_name ILIKE ? OR postal_address ILIKE ? OR created_by ILIKE ? " +
                "OR department ILIKE ? OR position ILIKE ? OR role ILIKE ? OR qualification ILIKE ? OR emp_id::text ILIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set the search parameters for each field
            for (int i = 1; i <= 9; i++) {
                ps.setString(i, "%" + searchQuery + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int empId = rs.getInt("emp_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String address = rs.getString("postal_address");
                    String createdBy = rs.getString("created_by");
                    String department = rs.getString("department");
                    String position = rs.getString("position");
                    String role = rs.getString("role");
                    String qualification = rs.getString("qualification");

                    // Create an Employee object and add it to the list
                    Employee employee = new Employee(empId, firstName, lastName, address, createdBy, department, position, role, qualification);
                    employeeList.add(employee);
                }
            }

            // Check if the employee list is empty
            if (employeeList.isEmpty()) {
                // Set an error message if no employees are found
                request.setAttribute("errorMessage", "No employees found matching the search criteria.");
            } else {
                // Set the employee list as a request attribute to pass to JSP
                request.setAttribute("employees", employeeList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Set an error message for database errors
            request.setAttribute("errorMessage", "An error occurred while fetching employee data.");
        } finally {
            // Close the database connection
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Forward the request to employeeList.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/employeeList.jsp");
        dispatcher.forward(request, response);
    }
}