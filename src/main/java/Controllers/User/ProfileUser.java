package Controllers.User;

import Models.User;
import Services.AuthService;
import Services.UserService;
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
@WebServlet(name = "ProfileUser", urlPatterns = {"/profile"})
public class ProfileUser extends HttpServlet {

    private static final String FORM_PATH = "/user/profile.jsp";

    @Inject
    private AuthService authService;

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = authService.getUser(request);
        if (user == null) {
            response.sendRedirect("/");
            return;
        }
        request.setAttribute("firstname", user.getFirstName());
        request.setAttribute("lastname", user.getLastName());
        request.setAttribute("email", user.getEmail());
        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = authService.getUser(request);
        if (user == null) {
            response.sendRedirect("/");
            return;
        }
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        request.setAttribute("firstname", firstName);
        request.setAttribute("lastname", lastName);
        User updatedUser;
        try {
            updatedUser = userService.updateUser(user);
        } catch (IllegalArgumentException ex) {
            request.setAttribute(ex.getMessage(), true);
            request.getRequestDispatcher(FORM_PATH).forward(request, response);
            return;
        }
        if (updatedUser == null) {
            request.setAttribute("status", "failed");
        } else {
            request.setAttribute("status", "successful");
        }
        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

}
