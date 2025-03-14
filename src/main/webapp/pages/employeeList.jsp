<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Employee" %>

<!-- Search Form -->
<form method="get" action="displayEmployee">
    <input type="text" name="search" placeholder="Search by Name, Position, Department, Role, Qualification, or ID" value="<%= request.getParameter("search") %>">
    <input type="submit" value="Search">
</form>

<!-- Employee Table -->
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Position</th>
        <th>Department</th>
        <th>Role</th>
        <th>Qualification</th>
        <th>Actions</th>
    </tr>

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
                        <a href="UpdateEmployeeServlet?id=<%= emp.getEmpId() %>">Edit</a>

                        <!-- Delete Button Form -->
                        <button type="button" onclick="showDeleteModal(<%= emp.getEmpId() %>)">Delete</button>
                    </td>
                </tr>
    <%
            }
        }
    %>
</table>

<!-- Modal for Confirmation -->
<div id="deleteModal" style="display:none;" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
    <div class="modal-content">
        <h2 id="modalTitle">Delete Confirmation</h2>
        <p>Are you sure you want to delete this employee?</p>
        <button id="confirmDelete" onclick="deleteEmployee()">Yes, Delete</button>
        <button onclick="closeDeleteModal()">Cancel</button>
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
        console.log("Employee ID passed to modal: " + empId); // Debugging line

        // Show the modal
        document.getElementById("deleteModal").style.display = "flex";

        // Set the employee ID to delete in the hidden form field
        document.getElementById("employeeIdToDelete").value = empId;

        // Debugging to check if value is correctly assigned
        console.log("Hidden field value set to: " + document.getElementById("employeeIdToDelete").value);
        document.getElementById("deleteModal").setAttribute("aria-hidden", "false");
    }

    // Function to hide the delete confirmation modal
    function closeDeleteModal() {
        document.getElementById("deleteModal").style.display = "none";
        document.getElementById("deleteModal").setAttribute("aria-hidden", "true");
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
    /* Basic styling for the modal */
    #deleteModal {
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
        text-align: center;
        border-radius: 10px;
        width: 300px;
    }

    .modal-content button {
        margin: 10px;
    }

    /* Style for the confirmation modal buttons */
    .modal-content button:first-child {
        background-color: red;
        color: white;
    }

    .modal-content button:last-child {
        background-color: gray;
        color: white;
    }
</style>
