<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Create Employee</title>
</head>
<body>
    <h2>Create New Employee</h2>

    <!-- Employee Creation Form -->
    <form action="createEmployee" method="POST">
        <!-- Employee First Name -->
        <label for="firstName">First Name:</label>
        <input type="text" name="firstName" id="firstName" required><br><br>

        <!-- Employee Last Name -->
        <label for="lastName">Last Name:</label>
        <input type="text" name="lastName" id="lastName" required><br><br>

        <!-- Employee Phone -->
        <label for="phone">Phone:</label>
        <input type="tel" name="phone" id="phone" required><br><br>

        <!-- Employee Address -->
        <label for="address">Address:</label>
        <input type="text" name="address" id="address" required><br><br>

        <!-- Employee Created By (HRM) -->
        <label for="createdBy">Created By:</label>
        <input type="text" name="createdBy" id="createdBy" value="HRM" readonly><br><br>

        <!-- Employee Email -->
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required><br><br>

        <!-- Employee Department -->
        <label for="department">Department:</label>
        <select name="department" id="department" required>
            <option value="">Select Department</option>
            <!-- Different Departments -->
            <option value="Human Resources">Human Resources</option>
            <option value="Finance">Finance</option>
            <option value="Sales">Sales</option>
            <option value="Marketing">Marketing</option>
            <option value="IT">IT</option>
            <option value="Operations">Operations</option>
            <option value="Legal">Legal</option>
            <option value="R&D">R&D</option>
            <option value="Customer Support">Customer Support</option>
        </select><br><br>

        <!-- Position (Leadership & Management Roles) -->
        <label for="position">Position:</label>
        <select name="position" id="position" required>
            <option value="">Select Position</option>
            <!-- Leadership & Management Positions -->
            <option value="Team Lead">Team Lead</option>
            <option value="Manager">Manager</option>
            <option value="Department Head">Department Head</option>
            <option value="Director">Director</option>
            <option value="Vice President">Vice President</option>
        </select><br><br>

        <!-- Role (Operational & Functional Roles) -->
        <label for="role">Role:</label>
        <select name="role" id="role" required>
            <option value="">Select Role</option>
            <!-- Operational & Functional Roles -->
            <option value="Operations Manager">Operations Manager</option>
            <option value="Functional Analyst">Functional Analyst</option>
            <option value="Team Leader">Team Leader</option>
            <option value="Quality Assurance">Quality Assurance</option>
            <option value="Logistics Coordinator">Logistics Coordinator</option>
            <option value="Supply Chain Manager">Supply Chain Manager</option>
        </select><br><br>

        <!-- Qualification (Different Levels of Education) -->
        <label for="qualification">Qualification:</label>
        <select name="qualification" id="qualification" required>
            <option value="">Select Qualification</option>
            <!-- Different Levels of Education -->
            <option value="High School">High School</option>
            <option value="Associate's Degree">Associate's Degree</option>
            <option value="Bachelor's Degree">Bachelor's Degree</option>
            <option value="Master's Degree">Master's Degree</option>
            <option value="Ph.D.">Ph.D.</option>
        </select><br><br>

        <!-- Submit Button -->
        <button type="submit">Create Employee</button>
    </form>

    <!-- Back to Dashboard -->
    <a href="${pageContext.request.contextPath}/hrm">Back to Dashboard</a>
</body>
</html>
