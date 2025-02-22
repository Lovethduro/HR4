package org.example.filters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/LoginServlet") // Adjust to your login servlet URL
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String username = httpRequest.getParameter("username");
        String password = httpRequest.getParameter("password");

        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/users"; // Change to your DB name
        String user = "postgres"; // PostgreSQL username
        String pass = "Rufus@1122"; // Your password

        try {
            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");

            // Establish connection
            Connection conn = DriverManager.getConnection(url, user, pass);

            // SQL query to check user credentials
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // User exists, proceed to the next filter or servlet
                chain.doFilter(request, response);
            } else {
                // User not found, redirect to login page with an error message
                httpResponse.sendRedirect("LoginServlet?error=invalid"); // Adjust the redirect path as needed
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            httpResponse.getWriter().println("❌ JDBC Driver not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            httpResponse.getWriter().println("❌ Unexpected Error: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
