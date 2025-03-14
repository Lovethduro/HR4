<%@ page import="org.example.model.Employee" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.example.servlets.DatabaseConnection" %>

<%
    // Get the employee ID from the request
    String empIdParam = request.getParameter("id");
    if (empIdParam == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Employee ID is missing.");
        return;
    }

    int empId = Integer.parseInt(empIdParam);
    Employee employee = null;

    // Fetch employee details from the database using the employee ID
    try (Connection conn = DatabaseConnection.connectToDatabase()) {
        String sql = "SELECT * FROM employee WHERE emp_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employee = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("postal_address"),
                        rs.getString("created_by"),
                        rs.getString("department"),
                        rs.getString("position"),
                        rs.getString("role"),
                        rs.getString("qualification")
                    );
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    if (employee == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found.");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Employee</title>
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

        input[type="text"] {
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
    <h2>Edit Employee</h2>

    <form method="post" action="UpdateEmployeeServlet">
        <!-- Hidden field for employee ID -->
        <input type="hidden" name="empId" value="<%= employee.getEmpId() %>">

        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" name="firstName" value="<%= employee.getFirstName() %>" required>
        </div>

        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" name="lastName" value="<%= employee.getLastName() %>" required>
        </div>

        <div class="form-group">
            <label for="address">Address:</label>
            <input type="text" name="address" value="<%= employee.getAddress() %>" required>
        </div>

        <div class="form-group">
            <label for="createdBy">Created By:</label>
            <input type="text" name="createdBy" value="<%= employee.getCreatedBy() %>" required>
        </div>

        <div class="form-group">
            <label for="department">Department:</label>
            <input type="text" name="department" value="<%= employee.getDepartment() %>" required>
        </div>

        <div class="form-group">
            <label for="position">Position:</label>
            <input type="text" name="position" value="<%= employee.getPosition() %>" required>
        </div>

        <div class="form-group">
            <label for="role">Role:</label>
            <input type="text" name="role" value="<%= employee.getRole() %>" required>
        </div>

        <div class="form-group">
            <label for="qualification">Qualification:</label>
            <input type="text" name="qualification" value="<%= employee.getQualification() %>" required>
        </div>

        <input type="submit" value="Update Employee">
    </form>

    <!-- Link to go back to the employee list page -->
    <a href="displayEmployee">Back to Employee List</a>
</body>
</html>