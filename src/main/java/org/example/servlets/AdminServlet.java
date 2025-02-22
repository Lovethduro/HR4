package org.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.counters.SessionCounter;
import org.example.counters.RequestCounter; // Import the RequestCounter
import com.google.gson.Gson; // Add Gson for JSON serialization

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Increment the request counter
            RequestCounter.increment();

            HttpSession session = request.getSession(false);

            // Debugging: Print session information
            System.out.println("Session: " + (session != null ? session.getId() : "No session"));
            System.out.println("Active sessions: " + SessionCounter.getActiveSessions());
            System.out.println("Total requests: " + RequestCounter.getRequestCount()); // Debug: Print request count

            // Check if the session exists and the user is an admin
            if (session != null && session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("admin")) {
                System.out.println("User is an admin. Rendering admin dashboard.");

                // Get active sessions count and request count
                int activeSessionCount = SessionCounter.getActiveSessions();
                int totalRequests = RequestCounter.getRequestCount();

                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();

                // Output the admin dashboard HTML
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Admin Dashboard</title>");
                out.println("<link rel='stylesheet' href='assets/css/style4.css'>"); // Link to CSS file
                out.println("</head>");
                out.println("<body>");

                // Header with picture and name
                out.println("<div class='header'>");
                out.println("<div class='admin-info'>");
                out.println("<div class='admin-picture'>");
                out.println("<img src='images/pic.jpg' alt='Admin Picture' />"); // Correct image path
                out.println("</div>");
                out.println("<p id='admin-name'>Loveth Rufus</p>");
                out.println("</div>");
                out.println("</div>");

                // Main content
                out.println("<div class='content'>"); // Wrap content in a div for better styling
                out.println("<h1>Welcome Rufus!</h1>");
                out.println("<p>" + activeSessionCount + " users are connected</p>"); // Display active sessions count
                out.println("<p>Total requests: " + totalRequests + "</p>"); // Display total requests
                // out.println("<p>User is an admin.</p>"); // Display admin status
               // out.println("<h2>Active Sessions</h2>");
               // out.println("<ul id='session-list'>");
               // out.println("<!-- Active sessions will be displayed here -->");
                out.println("</ul>");
                out.println("</div>");

                // Embed active sessions data as JSON for JavaScript
                String activeSessionsJson = new Gson().toJson(SessionCounter.getActiveSessionDetails());
                System.out.println("Active sessions JSON: " + activeSessionsJson); // Debug: Print JSON data
                out.println("<script>");
                out.println("const activeSessions = " + activeSessionsJson + ";");
                out.println("</script>");

                // Add JavaScript to dynamically populate the session list
                out.println("<script>");
                out.println("console.log('Active sessions data:', activeSessions);"); // Debug: Print JSON data
                out.println("const sessionList = document.getElementById('session-list');");
                out.println("if (sessionList) {");
                out.println("    if (activeSessions && activeSessions.length > 0) {");
                out.println("        console.log('Populating session list...');"); // Debug: Verify execution
                out.println("        activeSessions.forEach(session => {");
                out.println("            const li = document.createElement('li');");
                out.println("            li.textContent = session;");
                out.println("            sessionList.appendChild(li);");
                out.println("        });");
                out.println("    } else {");
                out.println("        console.log('No active sessions found.');"); // Debug: Verify execution
                out.println("        sessionList.innerHTML = '<li>No active sessions.</li>';");
                out.println("    }");
                out.println("} else {");
                out.println("    console.error(\"Element with ID 'session-list' not found.\");");
                out.println("}");
                out.println("</script>");

                out.println("</body>");
                out.println("</html>");
            } else {
                System.out.println("Redirecting to index.html due to insufficient permissions.");
                response.sendRedirect("index.html"); // Redirect to landing page if not admin
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error: An unexpected error occurred. Please try again later.</h2>");
        }
    }
}