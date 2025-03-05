<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <link rel="stylesheet" href="style2.css">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
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
                 z-index: 1000;
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
        <img src="images/close.png" alt="Back">
    </button>

    <form id="loginForm" action="login" method="post" onsubmit="setRememberMeCookie();">
        <h1>Login</h1>

        <div class="input-box">
            <input type="text" id="username" name="username" placeholder="Username" required>
            <i class='bx bxs-user'></i>
        </div>

        <div class="input-box">
            <input type="password" id="password" name="password" placeholder="Password" required>
            <i class='bx bxs-lock-alt' onclick="togglePassword()"></i>
        </div>

        <div class="remember-forgot">
            <label><input type="checkbox" id="rememberMe"> Remember me</label>
            <a href="forgot-password.jsp">Forgot password?</a>
        </div>

        <button type="submit" class="btn">Login</button>

        <div class="error-message" id="error-message">
            <%
                String error = request.getParameter("error");
                if (error != null) {
                    if (error.equals("missing_credentials")) {
                        out.println("Please enter your username and password.");
                    } else if (error.equals("invalid_credentials")) {
                        out.println("Invalid username or password. Please try again.");
                    }
                    out.println("<script>document.getElementById('error-message').style.display = 'block';</script>");
                }
            %>
        </div>

        <div class="register-link">
            <p>Don't have an account? <a href="index3.jsp">Register</a></p>
        </div>
    </form>
</div>

<script src="./assets/js/login.js"></script>
<script>
    // Function to set cookies when "Remember Me" is checked
    function setRememberMeCookie() {
        let username = document.getElementById("username").value;
        let rememberMe = document.getElementById("rememberMe").checked;

        if (rememberMe) {
            document.cookie = "username=" + username + "; path=/; max-age=" + (30 * 24 * 60 * 60); // 30 days
        } else {
            document.cookie = "username=; path=/; max-age=0"; // Clear cookie if unchecked
        }
    }

    // Function to get cookie value by name
    function getCookie(name) {
        let cookies = document.cookie.split("; ");
        for (let i = 0; i < cookies.length; i++) {
            let cookie = cookies[i].split("=");
            if (cookie[0] === name) {
                return cookie[1];
            }
        }
        return "";
    }

    // Auto-fill username field from cookies on page load
    window.onload = function() {
        let savedUsername = getCookie("username");
        if (savedUsername !== "") {
            document.getElementById("username").value = savedUsername;
            document.getElementById("rememberMe").checked = true;
        }

        // Check if there's an error query param
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('error')) {
            document.getElementById('error-message').style.display = 'block';
        }
    };
</script>
</body>
</html>