package org.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the login page (index2.jsp)
        request.getRequestDispatcher("/pages/index2.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe"); // Get the Remember Me checkbox value

        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String pass = "Rufus@1122";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");

            // Establish a connection to the database
            conn = DriverManager.getConnection(url, user, pass);

            // Query to retrieve user details, including the profile image (bytea)
            String sql = "SELECT first_name, last_name, role, profile_image FROM users WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // User found, create a session
                HttpSession session = request.getSession();

                // Retrieve user details from the result set
                String role = rs.getString("role");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                byte[] pictureBytes = rs.getBytes("profile_image"); // Retrieve the profile image as a byte array

                // Debugging: Log retrieved values
                System.out.println("Username: " + username);
                System.out.println("Role: " + role);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);

                // Store user details in the session
                session.setAttribute("user", username);
                session.setAttribute("userRole", role); // Store user role in session
                session.setAttribute("firstName", firstName); // Store first name
                session.setAttribute("lastName", lastName);   // Store last name
                session.setAttribute("picture", pictureBytes); // Store profile image as byte array

                // If Remember Me is checked, set a cookie
                if (rememberMe != null) {
                    Cookie cookie = new Cookie("username", username);
                    cookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
                    cookie.setPath("/"); // Make the cookie available across the entire application
                    response.addCookie(cookie);
                }

                // Redirect based on role
                if ("admin".equals(role)) {
                    System.out.println("Redirecting to admin dashboard.");
                    response.sendRedirect(request.getContextPath() + "/admin"); // Redirect to admin dashboard
                } else {
                    System.out.println("Redirecting to user dashboard.");
                    response.sendRedirect(request.getContextPath() + "/pages/dashboard.jsp"); // Redirect to user dashboard
                }
            } else {
                // Login failed, redirect with error parameter
                System.out.println("Login failed: Invalid credentials for user " + username);
                response.sendRedirect("login?error=invalid_credentials");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
            out.println("<script>alert('JDBC Driver not found: " + e.getMessage() + "'); window.location.href='index2.jsp';</script>");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            out.println("<script>alert('SQL Error: " + e.getMessage() + "'); window.location.href='index2.jsp';</script>");
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
            out.println("<script>alert('Unexpected Error: " + e.getMessage() + "'); window.location.href='index2.jsp';</script>");
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
