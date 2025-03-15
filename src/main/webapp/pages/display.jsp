<%@ page import="java.util.List" %>
<%@ page import="org.example.model.User" %>

<!-- Search Form -->
<form method="get" action="displayUser" class="search-form">
    <input type="text" name="search" placeholder="Search by Name, Email, Username, Role, or ID"
           value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
    <input type="submit" value="Search">
</form>

<!-- User Table -->
<table class="user-table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Username</th>
            <th>date of birth</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <%-- Loop through users --%>
        <%
            // Retrieve the user list from the request attribute
            List<User> users = (List<User>) request.getAttribute("users");

            if (users != null) {
                for (User user : users) {
        %>
                    <tr>
                        <td><%= user.getId() %></td>
                        <td><%= user.getFirstName() + " " + user.getLastName() %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getUsername() %></td>
                        <td><%= user.getDateOfBirth() %></td>
                        <td><%= user.getRole() %></td>
                        <td>
                            <a href="UpdateUserServlet?id=<%= user.getId() %>" class="edit-link">Edit</a>
                            <button type="button" onclick="showDeleteModal(<%= user.getId() %>)" class="delete-button">Delete</button>
                        </td>
                    </tr>
        <%
                }
            } else {
        %>
                <tr>
                    <td colspan="6" class="no-data">No users found.</td>
                </tr>
        <%
            }
        %>
    </tbody>
</table>
<a href="admin">Back to Dashboard</a>

<!-- Modal for Confirmation -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <h2>Delete Confirmation</h2>
        <p>Are you sure you want to delete this user?</p>
        <button id="confirmDelete" onclick="deleteUser()" class="confirm-button">Yes, Delete</button>
        <button onclick="closeDeleteModal()" class="cancel-button">Cancel</button>
    </div>
</div>

<!-- Hidden form for deletion -->
<form id="deleteForm" action="DeleteUserServlet" method="post" style="display:none;">
    <input type="hidden" name="user_id" id="userIdToDelete">
</form>

<script>
    // Function to show the delete confirmation modal
    function showDeleteModal(userId) {
        // Show the modal
        document.getElementById("deleteModal").style.display = "flex";

        // Set the user ID to delete in the hidden form field
        document.getElementById("userIdToDelete").value = userId;

        // Debugging to check if value is correctly assigned
        console.log("Hidden field value set to: " + document.getElementById("userIdToDelete").value);
    }

    // Function to hide the delete confirmation modal
    function closeDeleteModal() {
        document.getElementById("deleteModal").style.display = "none";
    }

    // Function to handle the deletion after confirmation
    function deleteUser() {
        var userId = document.getElementById("userIdToDelete").value;
        console.log("User ID to delete:", userId);  // Debugging statement

        // Submit the hidden form to delete the user
        if (userId) {
            document.getElementById("deleteForm").submit();
        } else {
            alert("User ID is missing.");
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

    /* User Table */
    .user-table {
        width: 100%;
        border-collapse: collapse;
        background-color: white;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .user-table th, .user-table td {
        padding: 12px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    .user-table th {
        background-color: #4a2c2a;
        color: white;
    }

    .user-table tr:hover {
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