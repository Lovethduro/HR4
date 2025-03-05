package org.example.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    // Handle GET requests (e.g., loading the registration form)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the registration page (index3.jsp)
        request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
    }

    // Handle POST requests (e.g., form submission)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String dob = request.getParameter("dob");

        // Input validation
        if (isAnyFieldEmpty(firstname, lastname, email, username, password, dob)) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
            return;
        }

        // Date parsing
        java.sql.Date sqlDate = parseDateOfBirth(dob, request, response);
        if (sqlDate == null) return; // Exit if date parsing failed

        // Database connection
        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String pass = "Rufus@1122";

        try {
            // Register the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            logger.debug("Attempting to connect to the database...");

            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                logger.info("Database connected successfully.");

                if (conn != null) {
                    logger.debug("Connection is valid: " + !conn.isClosed());
                } else {
                    logger.debug("Connection is null.");
                }

                // Insert user into database (store plaintext password)
                insertUserIntoDatabase(conn, firstname, lastname, email, username, password, sqlDate, request, response);
            }
        } catch (SQLException e) {
            logger.error("SQL Error: " + e.getMessage(), e);
            request.setAttribute("error", "SQL Error: " + e.getMessage());
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Unexpected Error: " + e.getMessage(), e);
            request.setAttribute("error", "Unexpected Error: " + e.getMessage());
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
        }
    }

    // Check if any field is empty
    private boolean isAnyFieldEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // Parse date of birth
    private java.sql.Date parseDateOfBirth(String dob, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat[] formats = {
                new SimpleDateFormat("MM/dd/yy"),
                new SimpleDateFormat("yyyy-MM-dd")
        };
        Date parsedDate = null;
        for (SimpleDateFormat format : formats) {
            try {
                parsedDate = format.parse(dob);
                break;
            } catch (ParseException e) {
                // Continue to the next format
            }
        }
        if (parsedDate == null) {
            request.setAttribute("error", "Invalid date format. Please use MM/dd/yy or yyyy-MM-dd.");
            request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
            return null; // Return null to indicate failure
        }
        return new java.sql.Date(parsedDate.getTime());
    }

    // Insert user into the database
    private void insertUserIntoDatabase(Connection conn, String firstname, String lastname, String email, String username,
                                        String password, java.sql.Date sqlDate, HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String sql = "INSERT INTO users (first_name, last_name, email, username, password, date_of_birth, role) VALUES (?, ?, ?, ?, ?, ?, 'user')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, email);
            stmt.setString(4, username);
            stmt.setString(5, password); // Store plaintext password
            stmt.setDate(6, sqlDate);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("User successfully registered.");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                logger.warn("User registration failed.");
                request.setAttribute("error", "User registration failed.");
                request.getRequestDispatcher("/pages/index3.jsp").forward(request, response);
            }
        }
    }
}
