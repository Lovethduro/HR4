package org.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User; // Import the User class
import org.example.servlets.DatabaseConnection; // Assuming you have a Database class to handle connections

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/UpdateUserServlet") // Updated URL mapping
public class UpdateUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id")); // Get the user ID from the request

        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Populate the user object with retrieved data
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                user.setRole(rs.getString("role"));

                // Set the user object as a request attribute to display in the form
                request.setAttribute("user", user);

                // Forward to the update form JSP
                RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/edituser.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        String role = request.getParameter("role");

        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, username = ?, date_of_birth = ?, role = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setDate(5, java.sql.Date.valueOf(dateOfBirth)); // Convert LocalDate to SQL Date
            ps.setString(6, role);
            ps.setInt(7, userId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("displayUser"); // Redirect to user list after update
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update user.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
}