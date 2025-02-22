package org.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String dob = request.getParameter("dob");

        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String pass = "Rufus@1122";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Database connected successfully.");

            // Date handling
            SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yy");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = null;

            try {
                parsedDate = format1.parse(dob);
            } catch (ParseException e) {
                try {
                    parsedDate = format2.parse(dob);
                } catch (ParseException e1) {
                    System.out.println("Date format error: " + e1.getMessage());
                    out.println("<script>alert('Date format error: " + e1.getMessage() + "'); window.location.href='index3.html';</script>");
                    return;
                }
            }

            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            String sql = "INSERT INTO users (first_name, last_name, email, username, password, date_of_birth, role) VALUES (?, ?, ?, ?, ?, ?, 'user')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, email);
            stmt.setString(4, username);
            stmt.setString(5, password);
            stmt.setDate(6, sqlDate);

            System.out.println("Executing query: " + sql);
            System.out.println("With values: " + firstname + ", " + lastname + ", " + email + ", " + username + ", " + password + ", " + sqlDate);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User successfully registered.");
                response.sendRedirect(request.getContextPath() + "/index2.html");
            } else {
                System.out.println("User registration failed.");
                response.sendRedirect("index3.html");
            }

            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
            out.println("<script>alert('JDBC Driver not found: " + e.getMessage() + "'); window.location.href='index3.html';</script>");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace(); // Print full SQL error in logs
            out.println("<script>alert('SQL Error: " + e.getMessage() + "'); window.location.href='index3.html';</script>");
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
            out.println("<script>alert('Unexpected Error: " + e.getMessage() + "'); window.location.href='index3.html';</script>");
        }
    }
}
