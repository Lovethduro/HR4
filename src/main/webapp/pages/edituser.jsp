<%@ page import="org.example.model.User" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.example.servlets.DatabaseConnection" %>

<%
    // Get the user ID from the request
    String userIdParam = request.getParameter("id");
    if (userIdParam == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is missing.");
        return;
    }

    int userId = Integer.parseInt(userIdParam);
    User user = null;

    // Fetch user details from the database using the user ID
    try (Connection conn = DatabaseConnection.connectToDatabase()) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                    user.setRole(rs.getString("role"));
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    if (user == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }

        h2 {
            color: #4a2c2a;
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            margin: 0 auto;
        }

        .form-group {
            display: grid;
            grid-template-columns: 120px 1fr;
            gap: 10px;
            align-items: center;
            margin-bottom: 15px;
        }

        label {
            font-weight: bold;
            color: #4a2c2a;
        }

        input[type="text"], input[type="date"], input[type="email"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            background-color: #f9f9f9;
            color: #4a2c2a;
        }

        input[type="submit"] {
            background-color: #8b5a2b;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            display: block;
            margin: 20px auto 0;
        }

        input[type="submit"]:hover {
            background-color: #6b4423;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #8b5a2b;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h2>Edit User</h2>

    <form method="post" action="UpdateUserServlet">
        <!-- Hidden field for user ID -->
        <input type="hidden" name="userId" value="<%= user.getId() %>">

        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" name="firstName" value="<%= user.getFirstName() %>" required>
        </div>

        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" name="lastName" value="<%= user.getLastName() %>" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" name="email" value="<%= user.getEmail() %>" required>
        </div>

        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" name="username" value="<%= user.getUsername() %>" required>
        </div>

        <div class="form-group">
            <label for="dateOfBirth">Date of Birth:</label>
            <input type="date" name="dateOfBirth" value="<%= user.getDateOfBirth() %>" required>
        </div>

        <div class="form-group">
            <label for="role">Role:</label>
            <input type="text" name="role" value="<%= user.getRole() %>" required>
        </div>

        <input type="submit" value="Update User">
    </form>

    <!-- Link to go back to the user list page -->
    <a href="displayUser">Back to User List</a>
</body>
</html>