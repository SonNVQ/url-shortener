package com.urlshortener.Controllers.User;

import com.urlshortener.Models.User;
import com.urlshortener.Services.AuthService;
import com.urlshortener.Services.UserService;
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
public class Profile extends HttpServlet {

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
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        
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
            request.setAttribute("status", "success");
        }
        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

}
