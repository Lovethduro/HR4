package org.example.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;

import java.io.IOException;
import jakarta.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.example.servlets.DatabaseConnection;

@WebServlet("/createUser")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Load the PostgreSQL driver
        DatabaseConnection.loadDriver();

        // Retrieve form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String dateOfBirthStr = request.getParameter("dateOfBirth");

        // Convert dateOfBirth to LocalDate
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);

        // Create a User object
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setDateOfBirth(dateOfBirth);
        user.setRole("user");

        // Save the user to the database
        boolean isSuccess = saveUserToDatabase(user);

        // Redirect based on success or failure
        if (isSuccess) {
            response.sendRedirect(request.getContextPath() + "/admin"); // Redirect to success page
        } else {
            response.sendRedirect("userCreationFailed.jsp"); // Redirect to failure page
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to the user creation form (user.jsp)
        response.sendRedirect(request.getContextPath() + "/pages/user.jsp");
    }

    private boolean saveUserToDatabase(User user) {
        String sql = "INSERT INTO users (first_name, last_name, email, username, password, date_of_birth, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) ";

        Connection conn = null;
        try {
            // Connect to the database
            conn = DatabaseConnection.connectToDatabase();

            // Prepare the SQL statement
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.setObject(6, user.getDateOfBirth());
            ps.setString(7, user.getRole());


            // Execute the query
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close the connection
            DatabaseConnection.closeConnection(conn);
        }
    }
}