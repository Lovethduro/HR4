<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Base64" %>
<%@ page isELIgnored="false" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style4.css"> <!-- Link to CSS file -->
    <style>
        /* General styles */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh; /* Ensure the footer stays at the bottom */
        }

        /* Header styles */
        .header {
            display: flex;
            justify-content: space-between; /* Space between left and right sections */
            align-items: center; /* Vertically center items */
            padding: 10px 20px;
            background-color: #f8f9fa;
            border-bottom: 1px solid #ddd;
        }

        /* Left section (session and request info) */
        .session-info {
            text-align: left;
        }

        .session-info p {
            margin: 0;
            font-size: 14px;
        }

        /* Right section (admin info and logout button) */
        .admin-info {
            display: flex;
            align-items: center;
            text-align: right;
        }

        .admin-picture {
            width: 50px;
            height: 50px;
            border-radius: 50%; /* Circular frame */
            overflow: hidden; /* Ensures the image stays within the circular frame */
            margin-right: 10px;
        }

        .admin-picture img {
            width: 100%;
            height: 100%;
            object-fit: cover; /* Ensures the image covers the circular frame */
        }

        #admin-name {
            margin: 0;
            font-size: 16px;
            font-weight: bold;
        }

        /* Logout button */
        .logout-button {
            margin-left: 20px;
        }

        .logout-button button {
            background-color: #dc3545; /* Red color for logout button */
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        .logout-button button:hover {
            background-color: #c82333; /* Darker red on hover */
        }

        /* Menu styles */
         .menu {
                    margin-top: 10px;
                    display: flex;
                    justify-content: space-around;
                    background-color: brown;
                    padding: 10px 0;
                    color: white;
                }

                .menu-item {
                    text-decoration: none;
                    color: white;
                    font-weight: bold;
                    padding: 10px 20px;
                    cursor: pointer; /* Change to pointer for button effect */
                }

                .menu-item:hover {
                    background-color: black;
                }

        /* Content styles */
        .content {
            padding: 20px;
            flex: 1; /* Ensures the content takes up remaining space */
        }

         .hidden {
                    display: none; /* Ensures hidden sections are not displayed */
                }

        .content h1 {
            margin-top: 0;
        }

        /* Footer styles */
        .footer {
            text-align: center;
            padding: 10px;
            background-color: #f8f9fa;
            border-top: 1px solid #ddd;
        }
    </style>
     <script>
            // Show the specified section and hide all others
            function showSection(sectionId) {
                // Hide all sections
                document.querySelectorAll('.content-section').forEach(section => {
                    section.classList.add('hidden');
                });
                // Show the selected section
                document.getElementById(sectionId).classList.remove('hidden');
            }
        </script>
</head>
<body>

    <div class="header">
        <!-- Session and request info (top left) -->
        <div class="session-info" style="flex:1;">
            <p>Active users connected: <%= org.example.counters.SessionCounter.getActiveSessions() %></p>
            <p>Total requests: <%= org.example.counters.RequestCounter.getRequestCount() %></p>
        </div>

        <!-- Admin info and logout button (top right) -->
        <div class="admin-info">
            <div class="admin-picture">
                <%
                    byte[] pictureBytes = (byte[]) request.getAttribute("pictureBytes");
                    if (pictureBytes != null) {
                        String base64Image = Base64.getEncoder().encodeToString(pictureBytes);
                %>
                    <img src="data:image/jpeg;base64,<%= base64Image %>" alt="Admin Picture" />
                <% } else { %>
                    <img src="${pageContext.request.contextPath}/images/default-pic.jpg" alt="Default Admin Picture" />
                <% } %>
            </div>
            <p id="admin-name">
                <%= request.getAttribute("firstName") + " " + request.getAttribute("lastName") %>
            </p>
            <div class="logout-button">
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit">Logout</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Menu -->
    <div class="menu">
            <span class="menu-item" onclick="showSection('home-section')">Home</span>
            <span class="menu-item" onclick="showSection('employees-section')">Employee</span>
            <span class="menu-item" onclick="showSection('user-section')">Users</span>
            <span class="menu-item" onclick="showSection('reports-section')">Reports</span>
        </div>

    <div class="content">
     <div id="home-section" class="content-section">
        <h1>Welcome <%= request.getAttribute("firstName") %>!</h1>
        <!-- Add more content here if needed -->
    </div>
    <div id="employees-section" class="content-section hidden">
                <h1>employees Section</h1>
                <form method="post" action="${pageContext.request.contextPath}/searchEmployees">
                               <input type="text" name="searchQuery" placeholder="Search Employees..." required>
                               <button type="submit">Search</button>
                           </form>

            </div>
            <div id="user-section" class="content-section hidden">
              <button onclick="window.location.href='${pageContext.request.contextPath}/createUser'" class="menu-items">Add Employee</button>
               <h2>user section</h2>
            </div>
            <div id="reports-section" class="content-section hidden">
                <h1>Reports Section</h1>
                <p>View application reports here.</p>
            </div>
        </div>



    <!-- Footer -->
    <div class="footer">
        <p>&copy; 2025 Your Company. All rights reserved.</p>
    </div>

</body>
</html>