package org.example.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebFilter("/LoginServlet") // Apply filter to LoginServlet
public class LoginAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Check if username and password are not empty
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            ((HttpServletResponse) response).sendRedirect("index2.html?error=missing_credentials");
            return;
        }

        // Validate credentials against the database
        if (!validateCredentials(username, password)) {
            ((HttpServletResponse) response).sendRedirect("index2.html?error=invalid_credentials");
            return;
        }

        chain.doFilter(request, response); // If valid, proceed to the servlet
    }

    private boolean validateCredentials(String username, String password) {
        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String pass = "Rufus@1122";
        boolean isValid = false;

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    isValid = rs.next(); // If there is a result, credentials are valid
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Optional initialization code
    }

    @Override
    public void destroy() {
        // Optional cleanup code
    }
}
