<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Admin Picture</title>
</head>
<body>
    <h2>Upload Admin Picture</h2>

    <!-- Debugging output to check if the page is loaded -->
    <%
        System.out.println("Upload JSP loaded."); // Check if the JSP is accessed
        String username = request.getParameter("username");
        if (username != null) {
            System.out.println("Received username: " + username); // Print received username
        } else {
            System.out.println("No username received.");
        }
    %>

    <!-- Form for uploading picture -->
    <form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
        <label>Username:</label>
        <input type="text" name="username" id="username" placeholder="Enter your username" required>
        <br><br>

        <label>Upload Image:</label>
        <input type="file" name="picture" accept="image/*" required>
        <br><br>

        <button type="submit">Upload</button>
    </form>

    <!-- Display any messages or errors here -->
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
            out.println("<p>" + message + "</p>");
        }
    %>
</body>
</html>