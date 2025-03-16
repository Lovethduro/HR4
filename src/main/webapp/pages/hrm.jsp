<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Base64" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HRM Dashboard</title>
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

        .menu-items {
                    text-decoration: none;
                    color: white;
                    font-weight: bold;
                    padding: 10px 20px;
                    cursor: pointer; /* Change to pointer for button effect */
                    background-color: brown;
                }

                .menu-items:hover {
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

    <!-- Menu -->
    <div class="menu">
        <span class="menu-item" onclick="showSection('home-section')">Home</span>
        <span class="menu-item" onclick="showSection('employees-section')">Employee</span>
        <span class="menu-item" onclick="showSection('reports-section')">Reports</span>
    </div>

    <div class="content">
        <div id="home-section" class="content-section">
            <h1>Welcome <%= request.getAttribute("firstName") %>!</h1>
            <!-- Welcome Section -->
            <div class="welcome-section">
                <h2>Welcome to Green Horizons Enterprises HR Dashboard</h2>
                <p>
                    Welcome to the official HR Dashboard of Green Horizons Enterprises! We are a leading company in the field of
                    sustainable solutions, dedicated to advancing environmental conservation while empowering our workforce.
                    At Green Horizons, we believe that our employees are the backbone of our success, and we are committed to
                    providing a work environment that promotes innovation, collaboration, and growth.
                </p>
                <p>
                    As a forward-thinking company, we understand that our success is directly tied to the well-being of our team.
                    This dashboard is designed to help you navigate all HR functions, providing quick access to essential employee
                    data, performance reports, and other key HR tools that help us maintain a motivated, efficient, and thriving workforce.
                    Here, you can manage employee records, monitor HR processes, and make informed decisions that contribute to our
                    collective success.
                </p>
                <p>
                    At Green Horizons Enterprises, sustainability is not just about the environment—it's about creating long-term,
                    meaningful impact for our employees, our clients, and our communities. We are passionate about developing
                    innovative solutions that benefit the planet and its people. Whether you're looking to manage HR data or gain insights
                    into employee performance, this dashboard provides everything you need to help us continue leading the way in
                    creating a brighter, greener future.
                </p>
                <p>
                    We thank you for being part of the Green Horizons family. Together, let's make a difference by working towards
                    a sustainable, innovative, and prosperous future for all.
                </p>
            </div>

        </div>
       <div id="employees-section" class="content-section hidden">
           <h1>Employee Management</h1>
           <p>
                   The Employee Management section is where you’ll be able to handle all aspects of employee information. Whether you’re
                   adding new employees, editing existing records, or reviewing past performance, this section provides you with the tools
                   needed for efficient HR management.
               </p>
               <p>
                   To add new employees, simply follow the process outlined in the "Add Employee" section. This allows you to input all
                   necessary details for new team members, ensuring that every record is accurately captured from the start.
               </p>
               <p>
                   The "Edit Employee" functionality enables you to make updates to existing records whenever changes need to be made,
                   such as address updates, position changes, or salary adjustments. This ensures that our employee data is always accurate
                   and up-to-date, and that any necessary changes are captured in real-time.
               </p>
               <p>
                   If you ever need to display detailed employee records, the "View Employee" section allows you to pull up information
                   on any employee in the system. Whether for reference, performance tracking, or administrative purposes, this functionality
                   provides a comprehensive view of all relevant details in a well-organized format.
               </p>
               <p>
                   In addition to viewing records, the "Search Employees" function makes it easy to quickly find individuals based on
                   specific attributes such as position, department, or performance metrics. This is an essential tool for HR staff to
                   efficiently search through the employee database without manually sifting through countless records.
               </p>
               <p>
                   For any employee that needs to be removed from the system, the "Delete Employee" feature ensures that their record
                   can be safely and permanently removed from the dashboard. This feature is crucial for maintaining an up-to-date database
                   of active employees and ensures that we keep our records accurate and organized.
               </p>
               <p>
                   Together, these tools empower you to effectively manage employee information, ensuring that all necessary data is readily
                   available and up-to-date. By having these features at your fingertips, managing the workforce at Green Horizons becomes a
                   streamlined, organized, and efficient process.
               </p>
           <!-- Button to navigate to createEmployee page -->
           <button onclick="window.location.href='${pageContext.request.contextPath}/createEmployee'" class="menu-items">Add Employee</button>
           <button onclick="window.location.href='${pageContext.request.contextPath}/displayEmployee'" class="menu-items">Display Employee</button>
       </div>


        <div id="reports-section" class="content-section hidden">
            <h1>Reports Section</h1>
            <!-- Reports Section -->
            <div class="reports-section">

                <p>
                    The Reports Section of the Green Horizons HR Dashboard plays a crucial role in helping HR staff, managers, and executives
                    make informed decisions. By providing a comprehensive suite of reports, this section allows you to track, analyze, and
                    assess key metrics across the organization. These reports are designed to give you a deeper understanding of various
                    aspects of employee performance, productivity, and organizational trends.
                </p>
                <p>
                    Whether you need to generate monthly performance reports, review employee turnover rates, or evaluate compensation
                    structures, the Reports Section provides all the necessary tools. These reports can help you make data-driven decisions
                    that improve employee engagement, reduce turnover, and optimize HR practices across the company.
                </p>
                <p>
                    One of the key advantages of the Reports Section is its ability to generate custom reports based on your needs.
                    You can select various filters and criteria to narrow down the data, allowing you to view reports that are relevant to
                    your specific objectives. This flexibility ensures that you have access to the most accurate and up-to-date information
                    when you need it.
                </p>
                <p>
                    The Reports Section is organized into several categories, each focused on a specific aspect of employee and company performance:
                </p>
                <ul>
                    <li><strong>Performance Reports:</strong> Track employee performance over time, evaluate goal achievement, and identify
                        areas for improvement. These reports are vital for performance reviews and promotions.</li>
                    <li><strong>Employee Retention Reports:</strong> Monitor employee retention rates, assess turnover trends, and identify
                        factors contributing to high or low retention.</li>
                    <li><strong>Compensation Reports:</strong> Review salary trends, bonus distributions, and ensure pay equity across the
                        company.</li>
                    <li><strong>Attendance and Absenteeism Reports:</strong> Track employee attendance patterns and manage absenteeism issues
                        to maintain productivity.</li>
                    <li><strong>Training and Development Reports:</strong> Evaluate the effectiveness of employee training programs and monitor
                        professional development progress.</li>
                </ul>
                <p>
                    Additionally, the Reports Section provides you with the option to export data, making it easy to share findings with
                    other teams or upper management. The ability to generate downloadable reports ensures that you have the necessary
                    documentation to support any decision-making processes or presentations.
                </p>
                <p>
                    To generate a report, simply select the type of report you wish to view, apply any relevant filters or date ranges, and
                    click "Generate Report." Once generated, you can either view the report online or download it for your records.
                    Reports can be saved for future reference, allowing you to track progress over time and compare results across different
                    periods.
                </p>
                <p>
                    By utilizing the Reports Section, you are equipped with the tools to monitor and optimize various aspects of HR management
                    in real-time. The insights gained from these reports help ensure that Green Horizons Enterprises continues to grow and
                    succeed in achieving its goals. With data at your fingertips, you can take proactive steps to improve employee satisfaction,
                    increase efficiency, and align HR practices with the overall vision of the company.
                </p>
            </div>

        </div>
    </div>

    <!-- Footer -->
    <div class="footer">
        <p>&copy; 2025 Green Horizons Enterprises. All rights reserved.</p>
    </div>
</body>
</html>