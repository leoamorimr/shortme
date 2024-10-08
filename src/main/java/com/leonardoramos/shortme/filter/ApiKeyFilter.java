package com.leonardoramos.shortme.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ApiKeyFilter implements Filter {

    private final List<String> bypassEndpoints = List.of("/r/");

    @Value("${api.key}")
    private String apiKey;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestApiKey = httpRequest.getHeader("X-API-KEY");
        String requestURI = httpRequest.getRequestURI();

        boolean isBypassed = bypassEndpoints.stream().anyMatch(requestURI::startsWith);

        if (isBypassed || apiKey.equals(requestApiKey)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {
    }
}
