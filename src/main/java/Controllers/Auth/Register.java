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
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "Register", urlPatterns = {"/auth/register"})
public class Register extends HttpServlet {

    private static final String VIEW_PATH = "register.jsp";

    @Inject
    private AuthService authService;

    public Register() {
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
        User user = processFormInfo(request, response);
        User registedUser;
        try {
            registedUser = authService.register(user);
        } catch (IllegalArgumentException ex) {
            request.setAttribute(ex.getMessage(), true);
            request.setAttribute("status", "failed");
            request.setAttribute("username", user.getUsername());
            request.setAttribute("firstname", user.getFirstName());
            request.setAttribute("lastname", user.getLastName());
            request.setAttribute("email", user.getEmail());
            viewDispatcher.forward(request, response);
            return;
        }
        if (registedUser == null) {
            request.setAttribute("status", "failed");
            request.setAttribute("username", user.getUsername());
            request.setAttribute("firstname", user.getFirstName());
            request.setAttribute("lastname", user.getLastName());
            request.setAttribute("email", user.getEmail());
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
