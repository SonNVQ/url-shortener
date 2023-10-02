package com.urlshortener.Controllers.User;

import com.urlshortener.Services.Impl.RoleService;
import com.urlshortener.Services.UrlService;
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
@WebServlet(name = "DeleteLinkUser", urlPatterns = {"/links/delete"})
public class DeleteLink extends HttpServlet {
    
    @Inject
    private UrlService urlService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath());
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = Integer.valueOf(request.getParameter("id"));
        Boolean isDeleted = urlService.deleteUrl(id);
        String redirectPath;
        if (RoleService.isAdmin(request)) {
            redirectPath = request.getContextPath() + "/admin/links/search";
        } else {
            redirectPath = request.getContextPath() + "/links/search";
        }
        if (isDeleted) {
            
            redirectPath += "?status=deleted-success";
        } else {
            redirectPath += "?status=deleted-failed";
        }
        response.sendRedirect(redirectPath);
    }
    
}
