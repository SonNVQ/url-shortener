package Controllers.Auth;

import Models.PasswordResetToken;
import Models.User;
import Services.PasswordResetService;
import Services.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
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
@WebServlet(name = "ForgotPassword", urlPatterns = {"/auth/forgotPassword"})
public class ForgotPassword extends HttpServlet {

    private static final String VIEW_PATH = "/auth/forgot-password.jsp";

    @Inject
    private PasswordResetService passwordResetService;

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(VIEW_PATH);
        String username = request.getParameter("username");
        User user = userService.getUserByUsername(username);
        if (user == null) {
            request.setAttribute("USERNAME_NOT_FOUND", true);
            requestDispatcher.forward(request, response);
            return;
        }
        PasswordResetToken token = passwordResetService.sendToken(user);
        if (token == null) {
            request.setAttribute("SEND_TOKEN_FAIL", true);
            requestDispatcher.forward(request, response);
            return;
        }
        request.setAttribute("status", "success");
        requestDispatcher.forward(request, response);
    }

}
