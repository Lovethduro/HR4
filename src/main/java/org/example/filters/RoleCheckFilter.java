package org.example.filters;



import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@jakarta.servlet.annotation.WebFilter("/admin/*") // Filter applies to all admin pages
public class RoleCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        if (session != null && session.getAttribute("userRole") != null) {
            String userRole = (String) session.getAttribute("userRole");

            // Check if the user is an admin
            if ("admin".equals(userRole)) {
                // Allow access to admin pages
                chain.doFilter(request, response);
            } else {
                // Redirect to an error page or access denied page
                httpResponse.sendRedirect("access-denied.html");
            }
        } else {
            // Redirect to the login page if the session is not valid
            httpResponse.sendRedirect("login.html");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}

