package com.urlshortener.Filters;

import com.urlshortener.Services.AuthService;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(
        filterName = "AdminRoleFilter",
        urlPatterns = {
            "/admin/*"
        }
)
public class AdminRoleFilter implements Filter {

    @Inject
    private AuthService authService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (!authService.isAdmin(request)) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        chain.doFilter(request, response);
    }

}
