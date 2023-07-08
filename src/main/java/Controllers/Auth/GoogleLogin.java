package Controllers.Auth;

import Models.User;
import Services.AuthService;
import Services.Impl.AuthServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
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
@WebServlet(name = "GoogleLogin", urlPatterns = {"/auth/login-google"})
public class GoogleLogin extends HttpServlet {

    private static final String VIEW_PATH = "login.jsp";

    @Inject
    private AuthService authService;

    public GoogleLogin() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher viewDispatcher = request.getRequestDispatcher(VIEW_PATH);
        User user;
        try {
            user = authService.googleLogin(request);
        } catch (IllegalArgumentException ex) {
            request.setAttribute(ex.getMessage(), true);
            viewDispatcher.forward(request, response);
            return;
        }
        if (user == null) {
            request.setAttribute("status", "google-failed");
            viewDispatcher.forward(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/");
    }

}
