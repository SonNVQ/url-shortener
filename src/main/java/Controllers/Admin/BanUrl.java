package Controllers.Admin;

import DAL.UrlDAO;
import DAL.UserDAO;
import Services.AuthService;
import Services.UrlService;
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
@WebServlet(name = "BanUrl", urlPatterns = {"/admin/links/ban"})
public class BanUrl extends HttpServlet {

    @Inject
    private UrlService urlService;

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
        
        urlService.banUrl(id);
        
        response.sendRedirect("/admin/links/search");
        
    }

}
