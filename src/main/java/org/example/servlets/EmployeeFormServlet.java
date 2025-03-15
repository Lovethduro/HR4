package org.example.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.servlets.DatabaseConnection;

@WebServlet("/createEmployee")
public class EmployeeFormServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pages/employeeForm.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        Connection conn = DatabaseConnection.connectToDatabase();
        if (conn != null) {
            try {
                if (isEmailOrPhoneExists(conn, email, phone)) {
                    response.getWriter().println("Error: Email or phone number already exists.");
                    return;
                }

                String sql = "INSERT INTO employee (first_name, last_name, phone, postal_address, email, department, position, role, qualification, created_by) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, firstName);
                    stmt.setString(2, lastName);
                    stmt.setString(3, phone);
                    stmt.setString(4, address);
                    stmt.setString(5, email);
                    stmt.setString(6, department);
                    stmt.setString(7, position);
                    stmt.setString(8, role);
                    stmt.setString(9, qualification);
                    stmt.setString(10, createdBy);

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        response.sendRedirect("displayEmployee");
                    } else {
                        response.getWriter().println("Failed to create employee.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Error: " + e.getMessage());
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            response.getWriter().println("Error: Unable to connect to the database.");
        }
    }

    private boolean isEmailOrPhoneExists(Connection conn, String email, String phone) throws SQLException {
        String checkSql = "SELECT 1 FROM employee WHERE email = ? OR phone = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, email);
            checkStmt.setString(2, phone);
            try (ResultSet rs = checkStmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
