package org.example.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User; // Import the User class
import org.example.servlets.DatabaseConnection; // Assuming you have a Database class to handle connections

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/displayUser") // Define the URL pattern for the servlet
public class DisplayUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("search");
        if (searchQuery == null) {
            searchQuery = "";
        }

        List<User> userList = new ArrayList<>();
        Connection conn = DatabaseConnection.connectToDatabase(); // Initialize your Database connection class

        // Modify the query to search multiple fields in the users table and filter by role = 'user'
        String sql = "SELECT id, first_name, last_name, email, username, date_of_birth, role " +
                "FROM users " +
                "WHERE (first_name ILIKE ? OR last_name ILIKE ? OR email ILIKE ? OR username ILIKE ? OR date_of_birth::text ILIKE ? OR id::text ILIKE ?) " +
                "AND role = 'user'"; // Add condition to filter by role

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set the search parameters for each field
            for (int i = 1; i <= 6; i++) { // Corrected to 6 parameters
                ps.setString(i, "%" + searchQuery + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email = rs.getString("email");
                    String username = rs.getString("username");
                    LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate(); // Retrieve date_of_birth
                    String role = rs.getString("role");

                    System.out.println("Retrieved Email: " + email);
                    // Create a User object and add it to the list
                    User user = new User();
                    user.setId(id);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setDateOfBirth(dateOfBirth); // Set date_of_birth
                    user.setRole(role);

                    userList.add(user);
                }
            }

            // Set the user list as a request attribute to pass to JSP
            request.setAttribute("users", userList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Forward the request to user.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/display.jsp");
        dispatcher.forward(request, response);
    }
}