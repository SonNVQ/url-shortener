package Controllers.Auth;

import Models.User;
import Services.AuthService;
import Utils.CookieUtils;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "Login", urlPatterns = {"/auth/login"})
public class Login extends HttpServlet {

    private static final String VIEW_PATH = "login.jsp";

    private static final int REMEMBER_ME_COOKIE_MAX_AGE = 60 * 60 * 24 * 7;

    @Inject
    private AuthService authService;

    public Login() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        String username = CookieUtils.getCookieValue(request, "username");
        if (username != null) {
            request.setAttribute("username", username);
            String password = CookieUtils.getCookieValue(request, "password");
            request.setAttribute("password", password);
        }
        request.getRequestDispatcher(VIEW_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher viewDispatcher = request.getRequestDispatcher(VIEW_PATH);
        User user;
        try {
            user = authService.login(request);
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
        Boolean rememberMe = Boolean.valueOf(request.getParameter("rememberMe"));
        if (rememberMe) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Cookie usernameCookie = new Cookie("username", username);
            Cookie passwordCookie = new Cookie("password", password);
            usernameCookie.setMaxAge(REMEMBER_ME_COOKIE_MAX_AGE);
            passwordCookie.setMaxAge(REMEMBER_ME_COOKIE_MAX_AGE);
            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
        } else {
            CookieUtils.deleteCookieByName(request, response, "username");
            CookieUtils.deleteCookieByName(request, response, "password");
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}
