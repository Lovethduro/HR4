<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Employee" %>

<!-- Search Form -->
<form method="get" action="displayEmployee" class="search-form">
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
            <th>Actions</th>
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
                        <td>
                            <a href="UpdateEmployeeServlet?id=<%= emp.getEmpId() %>" class="edit-link">Edit</a>
                            <button type="button" onclick="showDeleteModal(<%= emp.getEmpId() %>)" class="delete-button">Delete</button>
                        </td>
                    </tr>
        <%
                }
            } else {
        %>
                <tr>
                    <td colspan="7" class="no-data">No employees found.</td>
                </tr>
        <%
            }
        %>
    </tbody>
</table>
<a href="hrm">Back to Dashboard</a>

<!-- Modal for Confirmation -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <h2>Delete Confirmation</h2>
        <p>Are you sure you want to delete this employee?</p>
        <button id="confirmDelete" onclick="deleteEmployee()" class="confirm-button">Yes, Delete</button>
        <button onclick="closeDeleteModal()" class="cancel-button">Cancel</button>
    </div>
</div>

<!-- Hidden form for deletion -->
<form id="deleteForm" action="DeleteEmployeeServlet" method="post" style="display:none;">
    <input type="hidden" name="emp_id" id="employeeIdToDelete">
</form>

<script>
    // Function to show the delete confirmation modal
    function showDeleteModal(empId) {
        // Show the modal
        document.getElementById("deleteModal").style.display = "flex";

        // Set the employee ID to delete in the hidden form field
        document.getElementById("employeeIdToDelete").value = empId;

        // Debugging to check if value is correctly assigned
        console.log("Hidden field value set to: " + document.getElementById("employeeIdToDelete").value);
    }

    // Function to hide the delete confirmation modal
    function closeDeleteModal() {
        document.getElementById("deleteModal").style.display = "none";
    }

    // Function to handle the deletion after confirmation
    function deleteEmployee() {
        var employeeId = document.getElementById("employeeIdToDelete").value;
        console.log("Employee ID to delete:", employeeId);  // Debugging statement

        // Submit the hidden form to delete the employee
        if (employeeId) {
            document.getElementById("deleteForm").submit();
        } else {
            alert("Employee ID is missing.");
        }
    }
</script>

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

    /* Action Buttons */
    .edit-link {
        color: #4a2c2a;
        text-decoration: none;
    }

    .edit-link:hover {
        text-decoration: underline;
    }

    .delete-button {
        background-color: #ff4d4d;
        color: white;
        border: none;
        padding: 6px 12px;
        border-radius: 4px;
        cursor: pointer;
    }

    .delete-button:hover {
        background-color: #cc0000;
    }

    /* Modal Styles */
    .modal {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: none;
        justify-content: center;
        align-items: center;
        z-index: 1000;
    }

    .modal-content {
        background-color: white;
        padding: 20px;
        border-radius: 8px;
        text-align: center;
        width: 300px;
    }

    .confirm-button {
        background-color: #ff4d4d;
        color: white;
        border: none;
        padding: 8px 16px;
        border-radius: 4px;
        cursor: pointer;
    }

    .confirm-button:hover {
        background-color: #cc0000;
    }

    .cancel-button {
        background-color: #ccc;
        color: black;
        border: none;
        padding: 8px 16px;
        border-radius: 4px;
        cursor: pointer;
    }

    .cancel-button:hover {
        background-color: #999;
    }
</style>