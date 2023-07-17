package Controllers.Admin;

import DAL.UserDAO;
import Models.Role;
import Models.User;
import Services.AdminService;
import Services.AuthService;
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
@WebServlet(name = "RemoveAdminRole", urlPatterns = {"/admin/users/removeAdmin"})
public class RemoveAdminRole extends HttpServlet {

    @Inject
    private AdminService adminService;
    
    @Inject
    private AuthService authService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!authService.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        int id = Integer.valueOf(request.getParameter("id"));
        
        adminService.removeRoleAdmin(id);
        
        response.sendRedirect("/admin/users/search");
    }

}
