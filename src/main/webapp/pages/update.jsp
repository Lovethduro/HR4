<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Employee" %>

<!-- Search Form -->
<form method="get" action="displayEmployeee" class="search-form">
    <input type="text" name="search" placeholder="Search by Name, Position, Department, Role, Qualification, or ID"
           value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
    <input type="submit" value="Search">
</form>

<!-- Employee Table -->
<table class="employee-table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Position</th>
            <th>Department</th>
            <th>Role</th>
            <th>Qualification</th>
        </tr>
    </thead>
    <tbody>
        <%-- Loop through employees --%>
        <%
            // Retrieve the employee list from the request attribute
            List<Employee> employees = (List<Employee>) request.getAttribute("employees");

            if (employees != null) {
                for (Employee emp : employees) {
        %>
                    <tr>
                        <td><%= emp.getEmpId() %></td>
                        <td><%= emp.getName() %></td>
                        <td><%= emp.getPosition() %></td>
                        <td><%= emp.getDepartment() %></td>
                        <td><%= emp.getRole() %></td>
                        <td><%= emp.getQualification() %></td>
                    </tr>
        <%
                }
            } else {
        %>
                <tr>
                    <td colspan="6" class="no-data">No employees found.</td>
                </tr>
        <%
            }
        %>
    </tbody>
</table>
<a href="admin">Back to Dashboard</a>

<style>
    /* General Styles */
    body {
        font-family: Arial, sans-serif;
        background-color: #f5f5f5;
        margin: 0;
        padding: 20px;
    }

    /* Search Form */
    .search-form {
        margin-bottom: 20px;
        text-align: center;
    }

    .search-form input[type="text"] {
        padding: 8px;
        width: 300px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    .search-form input[type="submit"] {
        padding: 8px 16px;
        background-color: #4a2c2a;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .search-form input[type="submit"]:hover {
        background-color: #6b4423;
    }

    /* Employee Table */
    .employee-table {
        width: 100%;
        border-collapse: collapse;
        background-color: white;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .employee-table th, .employee-table td {
        padding: 12px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    .employee-table th {
        background-color: #4a2c2a;
        color: white;
    }

    .employee-table tr:hover {
        background-color: #f1f1f1;
    }

    .no-data {
        text-align: center;
        color: #888;
    }

    /* Back Link */
    .back-link {
        display: inline-block;
        margin-top: 20px;
        color: #4a2c2a;
        text-decoration: none;
    }

    .back-link:hover {
        text-decoration: underline;
    }
</style>