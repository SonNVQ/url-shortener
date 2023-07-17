package Controllers.Auth;

import Constants.Regex;
import Models.PasswordResetToken;
import Models.User;
import Services.PasswordResetService;
import Services.UserService;
import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "ResetPassword", urlPatterns = {"/auth/resetPassword"})
public class ResetPassword extends HttpServlet {

    private static final String VIEW_PATH = "/auth/reset-password.jsp";

    @Inject
    private PasswordResetService passwordResetService;

    @Inject
    private UserService userService;

    private final BcryptFunction bcrypt;

    public ResetPassword() {
        bcrypt = BcryptFunction.getInstance(Bcrypt.A, 10);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(VIEW_PATH);
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            request.setAttribute("LINK_IS_INVALID", true);
            requestDispatcher.forward(request, response);
            return;
        }
        PasswordResetToken token = passwordResetService.getToken(id);
        if (token == null) {
            request.setAttribute("LINK_IS_INVALID", true);
            requestDispatcher.forward(request, response);
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(token.getExpirationTime())) {
            request.setAttribute("TOKEN_IS_EXPIRED", true);
            requestDispatcher.forward(request, response);
            return;
        }
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(VIEW_PATH);
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            request.setAttribute("LINK_IS_INVALID", true);
            requestDispatcher.forward(request, response);
            return;
        }
        request.setAttribute("id", id);
        PasswordResetToken token = passwordResetService.getToken(id);
        if (token == null) {
            request.setAttribute("LINK_IS_INVALID", true);
            requestDispatcher.forward(request, response);
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(token.getExpirationTime())) {
            request.setAttribute("TOKEN_IS_EXPIRED", true);
            requestDispatcher.forward(request, response);
            return;
        }
        String password = request.getParameter("password");
        if (password == null || !password.matches(Regex.PASSWORD_CHECK)) {
            request.setAttribute("PASSWORD_IS_INVALID", true);
            requestDispatcher.forward(request, response);
            return;
        }
        String hashedPassword = Password.hash(password).with(bcrypt).getResult();
        Boolean isPasswordChanged = userService.changePassword(token.getUserId(), hashedPassword);
        if (!isPasswordChanged) {
            request.setAttribute("status", "failed");
            requestDispatcher.forward(request, response);
            return;
        }
        passwordResetService.deleteToken(id);
        request.setAttribute("status", "success");
        requestDispatcher.forward(request, response);
        return;
    }

}
