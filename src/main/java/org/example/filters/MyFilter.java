package org.example.filters;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")  // This applies the filter to all requests
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Pre-processing before the request is processed
        System.out.println("Filter is applied!");

        // Continue the request-response chain
        chain.doFilter(request, response);

        // Post-processing after the response is generated
        System.out.println("Filter completed!");
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
