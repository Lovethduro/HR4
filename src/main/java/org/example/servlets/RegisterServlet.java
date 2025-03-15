package org.example.servlets;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve form data
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String dob = request.getParameter("dob");
        String phone = request.getParameter("phone"); // Phone field retrieved last



        // Validate if any field is empty
        if (isAnyFieldEmpty(firstname, lastname, email, username, password, dob, phone)) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
            return;
        }

        // Validate phone number format
        if (!isValidPhoneNumber(phone)) {
            request.setAttribute("error", "Invalid phone number format. Please enter a valid number (10-15 digits).");
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
            return;
        }

        // Validate password strength
        if (!isValidPassword(password)) {
            request.setAttribute("error", "Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a digit, and a special character.");
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
            return;
        }

        // Validate username length
        if (!isValidUsername(username)) {
            request.setAttribute("error", "Username must be between 4 and 20 characters.");
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
            return;
        }

        // Parse date of birth
        java.sql.Date sqlDate = parseDateOfBirth(dob, request, response);
        if (sqlDate == null) return;

        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String pass = "Rufus@1122";

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                
                // Check if email, phone, or username already exists
                if (isEmailOrPhoneTaken(conn, email, phone, username)) {
                    request.setAttribute("error", "Email, phone number, or username already exists.");
                    request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
                    return;
                }

                // Insert user into the database
                insertUserIntoDatabase(conn, firstname, lastname, email, phone, username, password, sqlDate, request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Database error: " + e.getMessage(), e);
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
        }
    }

    private boolean isAnyFieldEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidPhoneNumber(String phone) {
        // Validate phone number format (10-15 digits)
        return phone.matches("\\d{10,15}");
    }

    private boolean isValidPassword(String password) {
        // Validate password strength
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(passwordPattern);
    }

    private boolean isValidUsername(String username) {
        // Validate username length
        return username.length() >= 4 && username.length() <= 20;
    }

    private java.sql.Date parseDateOfBirth(String dob, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parse date of birth in multiple formats
        SimpleDateFormat[] formats = {new SimpleDateFormat("MM/dd/yy"), new SimpleDateFormat("yyyy-MM-dd")};
        Date parsedDate = null;
        for (SimpleDateFormat format : formats) {
            try {
                parsedDate = format.parse(dob);
                break;
            } catch (ParseException ignored) {}
        }
        if (parsedDate == null) {
            request.setAttribute("error", "Invalid date format. Please use MM/dd/yy or yyyy-MM-dd.");
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
            return null;
        }
        return new java.sql.Date(parsedDate.getTime());
    }

    private boolean isEmailOrPhoneTaken(Connection conn, String email, String phone, String username) throws SQLException {
        // Check if email, phone, or username already exists in the database
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? OR phone = ? OR username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, phone);
            stmt.setString(3, username);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }

    private void insertUserIntoDatabase(Connection conn, String firstname, String lastname, String email, String phone, String username,
                                        String password, java.sql.Date sqlDate, HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        // Insert user into the database
        String sql = "INSERT INTO users (first_name, last_name, email, username, password, date_of_birth, role, phone) VALUES (?, ?, ?, ?, ?, ?, 'user', ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, email);// Phone field inserted last
            stmt.setString(4, username);
            stmt.setString(5, password);
            stmt.setDate(6, sqlDate);
            stmt.setString(7, phone); // Phone field inserted last

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                request.setAttribute("success", "User registered successfully!");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("error", "User registration failed.");
                request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            logger.error("Database error: " + e.getMessage(), e);
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
            return;
    }

    }
}