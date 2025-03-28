<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Signup Form</title>
    <link rel="stylesheet" href="style3.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <style>
        .back-button {
            position: absolute;
            top: 20px;
            left: 20px;
            background-color: #f0f0f0; /* Light background */
            border: none;
            padding: 8px 12px; /* Padding for the button */
            border-radius: 5px; /* Rounded corners */
            cursor: pointer;
            font-size: 14px; /* Font size */
            transition: background-color 0.3s;
        }

        .back-button:hover {
            background-color: #dcdcdc; /* Darker on hover */
        }

        .error-message {
            color: red; /* Error message color */
            margin-top: 10px; /* Margin for spacing */
        }
    </style>
</head>
<body>

<div class="wrapper">
    <button type="button" class="back-button" onclick="goBack();">
        <img src="images/close.png" alt="Back"></button>
    <form action="Register" method="post" id="signupForm" onsubmit="return validateForm();">
        <h1>Signup</h1>
        <div class="input-box">
            <input type="text" name="firstname" placeholder="First Name" required>
            <i class='bx bxs-user'></i>
        </div>
        <div class="input-box">
            <input type="text" name="lastname" placeholder="Last Name" required>
            <i class='bx bxs-user'></i>
        </div>
        <div class="input-box">
            <input type="date" name="dob" id="dob" placeholder="Date of Birth" required>
            <i class='bx bxs-calendar'></i>
        </div>
        <div class="input-box">
            <input type="text" name="username" placeholder="Username" required>
            <i class='bx bxs-user'></i>
        </div>
        <div class="input-box">
            <input type="email" name="email" placeholder="Email" required>
            <i class='bx bxs-envelope'></i>
        </div>
        <div class="input-box">
            <input type="password" name="password" placeholder="Password" required>
            <i class='bx bxs-lock-alt'></i>
        </div>
        <div class="input-box">
            <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
            <i class='bx bxs-lock'></i>
        </div>

        <!-- Phone Number Field (Moved to the bottom) -->
        <div class="input-box">
            <input type="tel" name="phone" placeholder="Phone Number" required>
            <i class='bx bxs-phone'></i>
        </div>

        <!-- Error message display -->
        <div class="error-message">
            <p id="error-message" style="color: red;">
                <%
                    String error =  (String) request.getAttribute("error");
                    if (error != null) {
                        out.println(error); // Display error message if available
                    }
                %>
            </p>
            <p id="success-message" style="color: green;">
                    <%
                        String success = (String) request.getAttribute("success");
                        if (success != null) {
                            out.println(success); // Display success message if available
                        }
                    %>
                </p>
        </div>

        <button type="submit" class="btn">Signup</button>
        <div class="register-link">
            <p>Already have an account? <a href="login">Login</a></p>
        </div>
    </form>
</div>
<script src="assets/js/scrpts.js"></script>
</body>
</html>