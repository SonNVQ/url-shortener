package Controllers.Auth;

import Models.User;
import Services.AuthService;
import Services.Impl.AuthServiceImpl;
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
@WebServlet(name = "Register", urlPatterns = {"/auth/register"})
public class Register extends HttpServlet {

    private static final String VIEW_PATH = "register.jsp";

    AuthService authService;

    public Register() {
        authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = processFormInfo(request, response);
        User registedUser;
        try {
            registedUser = authService.register(user);
        } catch (IllegalArgumentException ex) {
            request.setAttribute(ex.getMessage(), true);
            throw new AssertionError(ex);
        }
        if (registedUser == null) {
            request.setAttribute("status", "failed");
        } else {
            request.setAttribute("status", "successful");
        }
        request.getRequestDispatcher(VIEW_PATH).forward(request, response);
    }

    private User processFormInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("firstname", firstName);
        request.setAttribute("lastname", lastName);
        User user = User.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();
        return user;
    }
}
