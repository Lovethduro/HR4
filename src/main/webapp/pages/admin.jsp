<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Base64" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style4.css">
    <style>
        /* General styles */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        /* Header styles */
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
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
            border-radius: 50%;
            overflow: hidden;
            margin-right: 10px;
        }

        .admin-picture img {
            width: 100%;
            height: 100%;
            object-fit: cover;
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
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        .logout-button button:hover {
            background-color: #c82333;
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
            cursor: pointer;
        }

        .menu-item:hover {
            background-color: black;
        }

        /* Content styles */
        .content {
            padding: 20px;
            flex: 1;
        }

        .hidden {
            display: none;
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

        /* Button styles */
        .menu-items {
            background-color: brown;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            text-decoration: none;
            margin: 5px;
        }

        .menu-items:hover {
            background-color: black;
        }
    </style>
    <script>
        function showSection(sectionId) {
            document.querySelectorAll('.content-section').forEach(section => {
                section.classList.add('hidden');
            });
            document.getElementById(sectionId).classList.remove('hidden');
        }
    </script>
</head>
<body>
    <div class="header">
        <div class="session-info" style="flex:1;">
            <p>Active users connected: <%= org.example.counters.SessionCounter.getActiveSessions() %></p>
            <p>Total requests: <%= org.example.counters.RequestCounter.getRequestCount() %></p>
        </div>
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

    <div class="menu">
        <span class="menu-item" onclick="showSection('home-section')">Home</span>
        <span class="menu-item" onclick="showSection('employees-section')">Employee</span>
        <span class="menu-item" onclick="showSection('user-section')">Users</span>
        <span class="menu-item" onclick="showSection('reports-section')">Reports</span>
    </div>

    <div class="content">
        <div id="home-section" class="content-section">
            <h1>Welcome <%= request.getAttribute("firstName") %>!</h1>
             <h1>Welcome to Green Horizons Enterprises</h1>

                <h2>About Us</h2>
                <p>
                    Green Horizons Enterprises is a forward-thinking company committed to sustainable solutions and
                    innovative business practices. As a leader in the HR industry, we strive to create a work environment
                    that fosters growth, efficiency, and employee well-being.
                </p>

                <h2>Our Mission</h2>
                <p>
                    Our mission is to empower businesses with effective workforce management solutions while prioritizing
                    sustainability and ethical business practices. We believe that a strong workforce is the foundation of
                    a successful company, and we are dedicated to supporting our employees in achieving their full potential.
                </p>

                <h2>Our Vision</h2>
                <p>
                    To be a global leader in HR solutions, known for innovation, integrity, and excellence in employee management.
                </p>

                <h2>Why Choose Us?</h2>
                <ul>
                    <li><strong>People-Centric Approach:</strong> We put our employees first, ensuring a productive and positive work environment.</li>
                    <li><strong>Innovation-Driven:</strong> We leverage technology to streamline HR processes and improve operational efficiency.</li>
                    <li><strong>Sustainability:</strong> Our eco-friendly initiatives contribute to a greener future for businesses and communities.</li>
                </ul>

                <h2>What We Offer</h2>
                <p>At Green Horizons Enterprises, we provide:</p>
                <ul>
                    <li>✔ A dynamic and inclusive work environment.</li>
                    <li>✔ Cutting-edge HR solutions for employee management.</li>
                    <li>✔ Continuous learning and career development opportunities.</li>
                </ul>
        </div>
        <div id="employees-section" class="content-section hidden">
             <h2>Our Workforce</h2>
                <p>
                    At Green Horizons Enterprises, our employees are the backbone of our success. We prioritize a collaborative
                    and growth-driven work culture that ensures our team members excel in their respective roles.
                </p>

                <h2>Employee Benefits</h2>
                <ul>
                    <li>✔ Competitive salaries and performance-based bonuses.</li>
                    <li>✔ Career development programs and skill enhancement training.</li>
                    <li>✔ Comprehensive health and wellness benefits.</li>
                    <li>✔ A diverse and inclusive work environment.</li>
                </ul>

                <h2>Employee Operations</h2>
                <p>
                    As an admin, you can manage employees efficiently by ensuring compliance with company policies.
                </p>
            <button onclick="window.location.href='${pageContext.request.contextPath}/displayEmployeee'" class="menu-items">Display Employee</button>
        </div>
        <div id="user-section" class="content-section hidden">
            <h2>Managing System Users</h2>
                <p>
                    Our HR system ensures seamless user management by providing secure access and role-based authentication
                    for employees, HR managers, and administrators. Each user is assigned a role to maintain structured
                    access control within the system.
                </p>

                <h2>User Roles</h2>
                <ul>
                    <li>✔ <strong>Admin:</strong> Full control over employee records, user management, and system reports.</li>
                    <li>✔ <strong>HR Manager:</strong> Manages employee details, roles, and workforce-related operations.</li>
                    <li>✔ <strong>Employee:</strong> Limited access to personal information and work-related tools.</li>
                </ul>

                <h2>Security & Authentication</h2>
                <p>
                    To maintain system integrity, all users must authenticate using their credentials. Password protection
                    and role-based access ensure data security and prevent unauthorized modifications.
                </p>

                <h2>User Operations</h2>
                <p>
                    As an admin, you have the authority to create new users, modify their roles, and deactivate accounts
                    when necessary.
                </p>

            <button onclick="window.location.href='${pageContext.request.contextPath}/createUser'" class="menu-items">Add User</button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/displayUser'" class="menu-items">Display Users</button>
        </div>
        <div id="reports-section" class="content-section hidden">
             <h1>Reports & Analytics</h1>

                <h2>Overview</h2>
                <p>
                    The Reports section provides valuable insights into employee data, system usage, and HR operations.
                    As an admin, you can generate and analyze reports to make informed decisions and improve workforce management.
                </p>

                <h2>Available Reports</h2>
                <ul>
                    <li>📊 <strong>Employee Reports:</strong> View detailed records of employees, including roles, qualifications, and employment history.</li>
                    <li>📈 <strong>System Usage Reports:</strong> Monitor user activity, login trends, and overall system engagement.</li>
                    <li>📅 <strong>Attendance & Leave Reports:</strong> Track employee attendance, leaves, and work schedules.</li>
                    <li>💼 <strong>HR Performance Reports:</strong> Evaluate HR efficiency through task completion and operational benchmarks.</li>
                </ul>

                <h2>Data Security & Accuracy</h2>
                <p>
                    All reports are generated based on real-time data, ensuring accuracy and reliability. Access to reports is
                    restricted based on user roles to maintain confidentiality and compliance with company policies.
                </p>

                <h2>Actions</h2>
                <p>
                    Use the reports feature to export data, visualize trends, and support strategic decision-making within the HR system.
                </p>

                <button onclick="window.location.href='${pageContext.request.contextPath}/generateReport'" class="menu-items">Generate Report</button>
                <button onclick="window.location.href='${pageContext.request.contextPath}/viewReports'" class="menu-items">View Reports</button>
        </div>
    </div>

    <div class="footer">
        <p>&copy; 2025 Green Horizons Enterprises. All rights reserved.</p>
    </div>
</body>
</html>