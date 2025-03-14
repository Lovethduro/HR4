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


@WebServlet("/UpdateEmployeeServlet")
public class UpdateEmployeeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int empId = Integer.parseInt(request.getParameter("id")); // Get the employee ID from the request

        String sql = "SELECT * FROM employee WHERE emp_id = ?";

        try (Connection conn = DatabaseConnection.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Populate the employee object with retrieved data
                Employee emp = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("postal_address"),
                        rs.getString("created_by"),
                        rs.getString("department"),
                        rs.getString("position"),
                        rs.getString("role"),
                        rs.getString("qualification")
                );

                // Set the employee object as a request attribute to display in the form
                request.setAttribute("employee", emp);

                // Forward to the update form JSP
                RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/edit.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int empId = Integer.parseInt(request.getParameter("empId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("postal_address");
        String createdBy = request.getParameter("createdBy");
        String department = request.getParameter("department");
        String position = request.getParameter("position");
        String role = request.getParameter("role");
        String qualification = request.getParameter("qualification");

        String sql = "UPDATE employee SET first_name = ?, last_name = ?, postal_address = ?, created_by = ?, " +
                "department = ?, position = ?, role = ?, qualification = ? WHERE emp_id = ?";

        try (Connection conn = DatabaseConnection.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, address);
            ps.setString(4, createdBy);
            ps.setString(5, department);
            ps.setString(6, position);
            ps.setString(7, role);
            ps.setString(8, qualification);
            ps.setInt(9, empId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("displayEmployee"); // Redirect to employee list after update
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update employee.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
}

