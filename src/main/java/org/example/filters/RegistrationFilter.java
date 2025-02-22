package org.example.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebFilter("/RegisterServlet")  // This applies to the RegisterServlet
public class RegistrationFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed for this example
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String username = request.getParameter("username");

        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/users"; // Change DB name if needed
        String user = "postgres";
        String pass = "Rufus@1122";

        try {
            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");

            // Establish connection
            Connection conn = DriverManager.getConnection(url, user, pass);

            // 🔍 **Check if email or username already exists**
            String checkQuery = "SELECT COUNT(*) FROM users WHERE email = ? OR username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            checkStmt.setString(2, username);

            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                // If username or email exists, block the request and show an error
                out.println("<script>alert('Error: Username or Email is already registered!'); window.location.href='index3.html';</script>");
                return;
            }

            // **If no duplicate, continue to the servlet**
            chain.doFilter(request, response);

            // Close resources
            rs.close();
            checkStmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            out.println("<script>alert('JDBC Driver not found: " + e.getMessage() + "');</script>");
        } catch (SQLException e) {
            out.println("<script>alert('SQL Error: " + e.getMessage() + "');</script>");
        }
    }

    public void destroy() {
        // No resources to clean up
    }
}
