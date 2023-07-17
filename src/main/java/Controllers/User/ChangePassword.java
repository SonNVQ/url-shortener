package Controllers.User;

import Models.User;
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

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "ChangePasswordUser", urlPatterns = {"/changePass"})
public class ChangePassword extends HttpServlet {

    private static final String VIEW_PATH = "/user/change-password.jsp";

    @Inject
    private UserService userService;

    private final BcryptFunction bcrypt;

    public ChangePassword() {
        bcrypt = BcryptFunction.getInstance(Bcrypt.A, 10);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(VIEW_PATH);
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String newPasswordRetype = request.getParameter("newPasswordRetype");

        if (oldPassword == null || newPassword == null || newPasswordRetype == null) {
            request.setAttribute("INPUT_IS_INVALID", true);
            requestDispatcher.forward(request, response);
            return;
        }

        User user = (User) request.getSession().getAttribute("user");

        boolean verified = Password.check(oldPassword, user.getPassword()).with(bcrypt);
        if (!verified) {
            request.setAttribute("OLD_PASSWORD_IS_WRONG", true);
            requestDispatcher.forward(request, response);
            return;
        }

        if (!newPassword.equals(newPasswordRetype)) {
            request.setAttribute("NEW_PASSWORD_DOES_NOT_MATCH", true);
            requestDispatcher.forward(request, response);
            return;
        }
        String hashedNewPassword = Password.hash(newPassword).with(bcrypt).getResult();

        boolean isPasswordChanged = userService.changePassword(user.getId(), hashedNewPassword);

        if (!isPasswordChanged) {
            request.setAttribute("status", "failed");
            requestDispatcher.forward(request, response);
            return;
        }
        request.setAttribute("status", "success");
        requestDispatcher.forward(request, response);
    }

}
