package org.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Employee;
import org.example.servlets.DatabaseConnection; // Assuming you have a Database class to handle connections

import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/displayEmployeee") // Define the URL pattern for the servlet
public class UpdateServlet extends HttpServlet {

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

                    employeeList.add(new Employee(empId, firstName, lastName, address, createdBy, department, position, role, qualification));
                }
            }

            // Set the employee list as a request attribute to pass to JSP
            request.setAttribute("employees", employeeList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Forward the request to employeeList.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/update.jsp");
        dispatcher.forward(request, response);
    }
}
