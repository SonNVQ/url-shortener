package com.urlshortener.Controllers.Admin;

import com.urlshortener.Services.AdminService;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "SetAdminRole", urlPatterns = {"/admin/users/setAdmin"})
public class SetAdminRole extends HttpServlet {

    @Inject
    private AdminService adminService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));

        adminService.setRoleAdmin(id);

        response.sendRedirect("/admin/users/search");
    }

}
