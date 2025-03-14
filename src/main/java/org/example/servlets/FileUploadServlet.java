package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/uploadServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 10,  // 10MB
        maxRequestSize = 1024 * 1024 * 20 // 20MB
)
public class FileUploadServlet extends HttpServlet {

    // This method will handle the form submission and image upload
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // Retrieve the uploaded image file
        Part filePart = request.getPart("imageFile"); // Get the file part from the form
        InputStream imageContent = filePart.getInputStream(); // Get the image content

        // Database connection
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Assuming you have a method for connecting to the database
            connection = DatabaseConnection.connectToDatabase();


            // SQL query to insert form data and image into the database
            String query = "INSERT INTO users (name, email, profile_picture) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query);

            // Set parameters for the form data
            statement.setString(1, name);
            statement.setString(2, email);

            // Set the image as a Blob in the database
            statement.setBlob(3, imageContent);

            // Execute the query
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                response.getWriter().write("Form submitted successfully with image!");
            } else {
                response.getWriter().write("Error occurred while saving form data.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error occurred: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

