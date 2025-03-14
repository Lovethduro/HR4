<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Create Employee</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5; /* Light gray background */
            color: #4a2c2a; /* Dark brown text color */
            margin: 0;
            padding: 20px;
        }

        h2 {
            color: #4a2c2a; /* Dark brown heading color */
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            background-color: #ffffff; /* White background for the form */
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            margin: 0 auto;
        }

        .form-group {
            display: grid;
            grid-template-columns: 120px 1fr; /* Label width and input width */
            gap: 10px; /* Space between label and input */
            align-items: center; /* Vertically align label and input */
            margin-bottom: 15px; /* Space between form groups */
        }

        label {
            font-weight: bold;
            color: #4a2c2a; /* Dark brown label color */
        }

        input[type="text"],
        input[type="tel"],
        input[type="email"],
        select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            background-color: #f9f9f9; /* Light gray background for inputs */
            color: #4a2c2a; /* Dark brown text color */
        }

        input[type="text"]:read-only {
            background-color: #e0e0e0; /* Light gray background for readonly inputs */
        }

        button[type="submit"] {
            background-color: #8b5a2b; /* Brown background for the submit button */
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            display: block;
            margin: 20px auto 0; /* Center the button */
        }

        button[type="submit"]:hover {
            background-color: #6b4423; /* Darker brown on hover */
        }

        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #8b5a2b; /* Brown color for the link */
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h2>Create New Employee</h2>

    <!-- Employee Creation Form -->
    <form action="createEmployee" method="POST">
        <!-- Employee First Name -->
        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" name="firstName" id="firstName" required>
        </div>

        <!-- Employee Last Name -->
        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" name="lastName" id="lastName" required>
        </div>

        <!-- Employee Phone -->
        <div class="form-group">
            <label for="phone">Phone:</label>
            <input type="tel" name="phone" id="phone" required>
        </div>

        <!-- Employee Address -->
        <div class="form-group">
            <label for="address">Address:</label>
            <input type="text" name="address" id="address" required>
        </div>

        <!-- Employee Created By (HRM) -->
        <div class="form-group">
            <label for="createdBy">Created By:</label>
            <input type="text" name="createdBy" id="createdBy" value="HRM" readonly>
        </div>

        <!-- Employee Email -->
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" required>
        </div>

        <!-- Employee Department -->
        <div class="form-group">
            <label for="department">Department:</label>
            <select name="department" id="department" required>
                <option value="">Select Department</option>
                <option value="Human Resources">Human Resources</option>
                <option value="Finance">Finance</option>
                <option value="Sales">Sales</option>
                <option value="Marketing">Marketing</option>
                <option value="IT">IT</option>
                <option value="Operations">Operations</option>
                <option value="Legal">Legal</option>
                <option value="R&D">R&D</option>
                <option value="Customer Support">Customer Support</option>
            </select>
        </div>

        <!-- Position (Leadership & Management Roles) -->
        <div class="form-group">
            <label for="position">Position:</label>
            <select name="position" id="position" required>
                <option value="">Select Position</option>
                <option value="Team Lead">Team Lead</option>
                <option value="Manager">Manager</option>
                <option value="Department Head">Department Head</option>
                <option value="Director">Director</option>
                <option value="Vice President">Vice President</option>
            </select>
        </div>

        <!-- Role (Operational & Functional Roles) -->
        <div class="form-group">
            <label for="role">Role:</label>
            <select name="role" id="role" required>
                <option value="">Select Role</option>
                <option value="Operations Manager">Operations Manager</option>
                <option value="Functional Analyst">Functional Analyst</option>
                <option value="Team Leader">Team Leader</option>
                <option value="Quality Assurance">Quality Assurance</option>
                <option value="Logistics Coordinator">Logistics Coordinator</option>
                <option value="Supply Chain Manager">Supply Chain Manager</option>
            </select>
        </div>

        <!-- Qualification (Different Levels of Education) -->
        <div class="form-group">
            <label for="qualification">Qualification:</label>
            <select name="qualification" id="qualification" required>
                <option value="">Select Qualification</option>
                <option value="High School">High School</option>
                <option value="Associate's Degree">Associate's Degree</option>
                <option value="Bachelor's Degree">Bachelor's Degree</option>
                <option value="Master's Degree">Master's Degree</option>
                <option value="Ph.D.">Ph.D.</option>
            </select>
        </div>

        <!-- Submit Button -->
        <button type="submit">Create Employee</button>
    </form>

    <!-- Back to Dashboard -->
    <a href="hrm">Back to Dashboard</a>
</body>
</html>