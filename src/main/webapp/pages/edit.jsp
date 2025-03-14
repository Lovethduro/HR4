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

<h2>Edit Employee</h2>

<form method="post" action="UpdateEmployeeServlet">
    <!-- Hidden field for employee ID -->
    <input type="hidden" name="empId" value="<%= employee.getEmpId() %>">

    <label for="firstName">First Name:</label>
    <input type="text" name="firstName" value="<%= employee.getFirstName() %>" required><br><br>

    <label for="lastName">Last Name:</label>
    <input type="text" name="lastName" value="<%= employee.getLastName() %>" required><br><br>

    <label for="address">Address:</label>
    <input type="text" name="address" value="<%= employee.getAddress() %>" required><br><br>

    <label for="createdBy">Created By:</label>
    <input type="text" name="createdBy" value="<%= employee.getCreatedBy() %>" required><br><br>

    <label for="department">Department:</label>
    <input type="text" name="department" value="<%= employee.getDepartment() %>" required><br><br>

    <label for="position">Position:</label>
    <input type="text" name="position" value="<%= employee.getPosition() %>" required><br><br>

    <label for="role">Role:</label>
    <input type="text" name="role" value="<%= employee.getRole() %>" required><br><br>

    <label for="qualification">Qualification:</label>
    <input type="text" name="qualification" value="<%= employee.getQualification() %>" required><br><br>

    <input type="submit" value="Update Employee">
</form>

<%-- Include a link to go back to the employee list page --%>
<a href="<%= request.getContextPath() %>/displayEmployee">Back to Employee List</a>

