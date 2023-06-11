package Controllers.Auth;

import Models.Role;
import Models.User;
import Services.AuthService;
import Services.Impl.AuthServiceImpl;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "Login", urlPatterns = {"/auth/login"})
public class Login extends HttpServlet {

    private static final String VIEW_PATH = "login.jsp";

    private final AuthService authService;

    public Login() {
        authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        request.getRequestDispatcher(VIEW_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher viewDispatcher = request.getRequestDispatcher(VIEW_PATH);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        request.setAttribute("username", username);
        User user;
        try {
            user = authService.login(username, password);
        } catch (IllegalArgumentException ex) {
            request.setAttribute(ex.getMessage(), true);
            viewDispatcher.forward(request, response);
            return;
        }
        if (user == null) {
            request.setAttribute("status", "failed");
            viewDispatcher.forward(request, response);
            return;
        }
        HashSet<Role> roles = authService.getUserRoles(user);
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        session.setAttribute("roles", roles);
        response.sendRedirect(request.getContextPath());
    }
}
