<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Create User</title>
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
        input[type="email"],
        input[type="password"],
        input[type="date"],
        input[type="file"] {
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

        button[type="submit"],
        button[type="button"] {
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

        button[type="submit"]:hover,
        button[type="button"]:hover {
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

        .error {
            color: red;
            font-size: 12px;
            margin-top: 5px;
        }

        .button-container {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h2>Create New User</h2>

    <!-- User Creation Form -->
    <form action="createUser" method="POST" enctype="multipart/form-data" onsubmit="return validateForm()">
        <!-- First Name -->
        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" name="firstName" id="firstName" required>
        </div>

        <!-- Last Name -->
        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" name="lastName" id="lastName" required>
        </div>

        <!-- Email -->
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" required>
        </div>

        <!-- Username -->
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" name="username" id="username" required>
        </div>

        <!-- Password -->
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" required>
        </div>

        <!-- Confirm Password -->
        <div class="form-group">
            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" name="confirmPassword" id="confirmPassword" required>
            <div id="passwordError" class="error"></div>
        </div>

        <!-- Date of Birth -->
        <div class="form-group">
            <label for="dateOfBirth">Date of Birth:</label>
            <input type="date" name="dateOfBirth" id="dateOfBirth" required>
            <div id="dobError" class="error"></div>
        </div>

        <!-- Profile Image (Optional) -->
        <div class="form-group">
            <label for="profileImage">Profile Image:</label>
            <input type="file" name="profileImage" id="profileImage" accept="image/*">
        </div>

        <!-- Button Container -->
        <div class="button-container">
            <!-- Submit Button -->
            <button type="submit">Create User</button>

            <!-- Upload Button -->
            <button type="button" onclick="redirectToUploadServlet()">Upload File</button>
        </div>
    </form>

    <!-- Back to Dashboard -->
    <a href="adminDashboard">Back to Dashboard</a>

    <script>
        function validateForm() {
            // Password confirmation validation
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;
            if (password !== confirmPassword) {
                document.getElementById("passwordError").innerText = "Passwords do not match.";
                return false;
            } else {
                document.getElementById("passwordError").innerText = "";
            }

            // Date of Birth validation
            const dob = document.getElementById("dateOfBirth").value;
            const dobDate = new Date(dob);
            const today = new Date();
            if (dobDate >= today) {
                document.getElementById("dobError").innerText = "Date of Birth cannot be in the future.";
                return false;
            } else {
                document.getElementById("dobError").innerText = "";
            }

            return true;
        }

        // Function to redirect to the upload servlet
        function redirectToUploadServlet() {
            window.location.href = "uploadServlet"; // Replace with your upload servlet URL
        }
    </script>
</body>
</html>