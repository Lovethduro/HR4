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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.example.servlets.DatabaseConnection;

@WebServlet("/createUser")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class AddUserServlet extends HttpServlet {

    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int PHONE_LENGTH = 10;

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
        String phone = request.getParameter("phone");

        // Convert dateOfBirth to LocalDate
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);

        // Validate password length
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            request.setAttribute("passwordError", "Password must be between 8 and 20 characters.");
            request.getRequestDispatcher("/pages/user.jsp").forward(request, response);
            return;
        }

        // Validate phone number length
        if (phone.length() != PHONE_LENGTH) {
            request.setAttribute("phoneError", "Phone number must be exactly 10 digits.");
            request.getRequestDispatcher("/pages/user.jsp").forward(request, response);
            return;
        }

        // Create a User object
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setDateOfBirth(dateOfBirth);
        user.setRole("user");
        user.setPhone(phone);

        // Check if email, username, or phone already exists
        if (isEmailTaken(email) || isUsernameTaken(username) || isPhoneTaken(phone)) {
            request.setAttribute("error", "Email, Username, or Phone number already exists.");
            request.getRequestDispatcher("/pages/user.jsp").forward(request, response);
            return;
        }

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
        request.getRequestDispatcher("/pages/user.jsp").forward(request, response);
    }

    private boolean saveUserToDatabase(User user) {
        String sql = "INSERT INTO users (first_name, last_name, email, username, password, date_of_birth, role, phone) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
            ps.setString(8, user.getPhone());

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

    private boolean isEmailTaken(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        return isFieldTaken(sql, email);
    }

    private boolean isUsernameTaken(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        return isFieldTaken(sql, username);
    }

    private boolean isPhoneTaken(String phone) {
        String sql = "SELECT COUNT(*) FROM users WHERE phone = ?";
        return isFieldTaken(sql, phone);
    }

    private boolean isFieldTaken(String sql, String fieldValue) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fieldValue);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // If any row is found, return true
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // If there's an error, assume the field is taken
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }
}
