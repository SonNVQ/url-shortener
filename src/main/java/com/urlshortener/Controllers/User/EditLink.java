package com.urlshortener.Controllers.User;

import com.urlshortener.Models.Url;
import com.urlshortener.Services.AuthService;
import com.urlshortener.Services.Impl.RoleService;
import com.urlshortener.Services.UrlService;
import jakarta.inject.Inject;
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
@WebServlet(name = "EditLinkUser", urlPatterns = {"/links/edit"})
public class EditLink extends HttpServlet {

    private static final String FORM_PATH = "/user/link-form.jsp";

    private static final String REDIRECT_PATH = "links/search";

    @Inject
    private AuthService authService;

    @Inject
    private UrlService urlService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uid = request.getParameter("uid");
        if (uid == null || uid.isEmpty()) {
            response.sendRedirect(REDIRECT_PATH);
            return;
        }
        int userId = authService.getUserId(request);
        Url url = urlService.getUrl(uid);
        if (!RoleService.isAdmin(request) && url.getUserId() != userId) {
            response.sendRedirect(REDIRECT_PATH);
            return;
        }
        request.setAttribute("url", url);
        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uid = request.getParameter("uid");
        if (uid == null || uid.isEmpty()) {
            response.sendRedirect(REDIRECT_PATH);
            return;
        }
        int user_id = authService.getUserId(request);
        Url url = urlService.getUrl(uid);
        if (!RoleService.isAdmin(request) && url.getUserId() != user_id) {
            response.sendRedirect(REDIRECT_PATH);
            return;
        }

        String title = request.getParameter("title");
        String passcode = request.getParameter("passcode");
        String redirectTimeString = request.getParameter("redirect-time");
        Integer redirectTime = null;
        if (!redirectTimeString.isEmpty()) {
            redirectTime = Integer.parseInt(redirectTimeString);
        }
        String redirectMessage = request.getParameter("redirect-message").trim();
        String expirationTimeString = request.getParameter("expiration-time");
        LocalDateTime expiratonTime = null;
        if (!expirationTimeString.isEmpty()) {
            expiratonTime = LocalDateTime.parse(expirationTimeString);
        }

        url.setTitle(title);
        url.setPasscode(passcode);
        url.setRedirectTime(redirectTime);
        url.setRedirectMessage(redirectMessage);
        url.setExpirationTime(expiratonTime);

        Url updatedUrl = urlService.updateUrl(url);
        if (updatedUrl != null) {
            request.setAttribute("status", "updated-success");
        } else {
            request.setAttribute("status", "updated-fail");
        }

        request.setAttribute("url", updatedUrl);
        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

}
