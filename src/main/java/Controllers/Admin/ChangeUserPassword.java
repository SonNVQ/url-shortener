package Controllers.Admin;

import Models.User;
import Services.AuthService;
import Services.UserService;
import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
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
@WebServlet(name = "ChangeUserPassword", urlPatterns = {"/admin/users/changePass"})
public class ChangeUserPassword extends HttpServlet {

    private static final String FORM_PATH = "/admin/user-pass.jsp";

    @Inject
    AuthService authService;

    @Inject
    UserService userService;

    private final BcryptFunction bcrypt;

    public ChangeUserPassword() {
        bcrypt = BcryptFunction.getInstance(Bcrypt.A, 10);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!authService.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!authService.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        Integer id = Integer.valueOf(request.getParameter("id"));
        String password = request.getParameter("password");
        String hashedPassword = Password.hash(password).with(bcrypt).getResult();
        User user = userService.getUser(id);
        user.setPassword(hashedPassword);
        boolean result = userService.changePassword(id, hashedPassword);
        if (result) {
            request.setAttribute("status", "updated-success");
        } else {
            request.setAttribute("status", "updated-failed");
        }
        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

}
