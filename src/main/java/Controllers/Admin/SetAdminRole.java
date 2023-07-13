package Controllers.Admin;

import DAL.UserDAO;
import Models.Role;
import Models.User;
import Services.AuthService;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author nguyenson
 */
@WebServlet(name="SetAdminRole", urlPatterns={"/admin/users/setAdmin"})
public class SetAdminRole extends HttpServlet {
   
    @Inject
    private UserDAO userDAO;
    
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
        User user = userDAO.getUserById(id);
        User updatedUser = userDAO.addUserRole(user, Role.ADMIN);
        response.sendRedirect("/admin/users/search");
    }

}
