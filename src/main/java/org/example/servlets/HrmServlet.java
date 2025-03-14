package org.example.servlets;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.example.counters.RequestCounter;
import org.example.model.Employee;

import java.util.logging.Logger;

@WebServlet("/hrm")
public class HrmServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(HrmServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Increment the request counter
            RequestCounter.increment();

            HttpSession session = request.getSession(false);

            // Debugging: Check if session exists
            logger.info("Session exists: " + (session != null));
            if (session != null) {
                // Debugging: Log the user role
                logger.info("User role: " + session.getAttribute("userRole"));
            }

            // Check if the session exists and the user is an admin
            if (session != null && session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("hrm")) {
                // Retrieve the admin's username from the session
                String username = (String) session.getAttribute("user");

                // Debugging: Log the username
                logger.info("Fetching admin details for user: " + username);

                // Fetch the admin's details from the database
                String url = "jdbc:postgresql://localhost:5432/users";
                String user = "postgres";
                String pass = "Rufus@1122";

                // Initialize admin details
                String firstName = "";
                String lastName = "";
                byte[] pictureBytes = null;

                // Query to get admin details
                String sql = "SELECT first_name, last_name, profile_image FROM users WHERE username = ?";
                try (Connection conn = DriverManager.getConnection(url, user, pass);
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, username);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        firstName = rs.getString("first_name");
                        lastName = rs.getString("last_name");
                        pictureBytes = rs.getBytes("profile_image");
                    }
                }

                // Set admin details as request attributes
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("pictureBytes", pictureBytes);

                // Forward to the JSP page
                request.getRequestDispatcher("/pages/hrm.jsp").forward(request, response);
            } else {
                // Redirect to landing page if not admin or session is invalid
                logger.info("Redirecting to landing page. User is not admin or session is invalid.");
                response.sendRedirect(request.getContextPath() + "/pages/index.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error: An unexpected error occurred. Please try again later.</h2>");
        }
    }

}
