package org.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Display the login form when the user accesses the /login URL
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Output the login form HTML
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Login Form</title>");
        out.println("<link rel='stylesheet' href='style2.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='wrapper'>");
        out.println("<form id='loginForm' action='login' method='post'>");
        out.println("<h1>Login</h1>");
        out.println("<div class='input-box'>");
        out.println("<input type='text' id='username' name='username' placeholder='Username' required>");
        out.println("</div>");
        out.println("<div class='input-box'>");
        out.println("<input type='password' id='password' name='password' placeholder='Password' required>");
        out.println("</div>");
        out.println("<div class='remember-forgot'>");
        out.println("<label><input type='checkbox' id='rememberMe' name='rememberMe'> Remember me</label>");
        out.println("<a href='forgot-password.html'>Forgot password?</a>");
        out.println("</div>");
        out.println("<button type='submit' class='btn'>Login</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
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

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);

            // Check username and password in the database
            String sql = "SELECT first_name, last_name, role FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // User found, create a session
                HttpSession session = request.getSession();
                String role = rs.getString("role");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                // Store user details in the session
                session.setAttribute("user", username);
                session.setAttribute("userRole", role); // Store user role in session
                session.setAttribute("loveth", firstName); // Store first name for admin
                session.setAttribute("rufus", lastName);   // Store last name for admin

                // If Remember Me is checked, set a cookie
                if (rememberMe != null) {
                    Cookie cookie = new Cookie("username", username);
                    cookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
                    cookie.setPath("/"); // Make the cookie available across the entire application
                    response.addCookie(cookie);
                }

                // Redirect based on role
                if ("admin".equals(role)) {
                    response.sendRedirect("admin"); // Redirect to admin servlet
                } else {
                    response.sendRedirect("https://cars.mclaren.com/en/super-series/720s"); // Redirect to user home page
                }
            } else {
                // Login failed, redirect with error parameter
                response.sendRedirect("login?error=invalid_credentials");
            }

            // Clean up
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            out.println("<script>alert('JDBC Driver not found: " + e.getMessage() + "'); window.location.href='login.html';</script>");
        } catch (SQLException e) {
            out.println("<script>alert('SQL Error: " + e.getMessage() + "'); window.location.href='login.html';</script>");
        } catch (Exception e) {
            out.println("<script>alert('Unexpected Error: " + e.getMessage() + "'); window.location.href='login.html';</script>");
        }
    }

}
