package com.urlshortener.Controllers.Url;

import com.urlshortener.DTO.UrlRequest;
import com.urlshortener.Models.Url;
import com.urlshortener.Services.AuthService;
import com.urlshortener.Services.UrlService;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "HomepageUrl", urlPatterns = {"/"})
public class Homepage extends HttpServlet {

    private static final String FORM_PATH = "/homepage.jsp";
    private static final String PASSCODE_PATH = "/url/passcode.jsp";
    private static final String REDIRECT_PATH = "/url/redirect.jsp";
    private static final String EXPIRED_PATH = "/url/expired.jsp";
    private static final String BANNED_PATH = "/url/banned.jsp";

    @Inject
    private UrlService urlService;

    @Inject
    private AuthService authService;

    public Homepage() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String uid = uri.substring(uri.lastIndexOf('/') + 1);
        if (uid.isEmpty()) {
            request.getRequestDispatcher(FORM_PATH).forward(request, response);
            return;
        }

        Url url = urlService.getUrl(uid);
        if (url == null) {
            request.getRequestDispatcher(FORM_PATH).forward(request, response);
            return;
        }

        if (url.getIsBanned()) {
            request.getRequestDispatcher(BANNED_PATH).forward(request, response);
            return;
        }

        LocalDateTime expirationTime = url.getExpirationTime();
        if (expirationTime != null && expirationTime.isBefore(LocalDateTime.now())) {
            Date date = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
            request.setAttribute("expiration_time", date);
            request.getRequestDispatcher(EXPIRED_PATH).forward(request, response);
            return;
        }
        request.setAttribute("uid", uid);
        if (url.getPasscode() != null && !url.getPasscode().isEmpty()) {
            request.getRequestDispatcher(PASSCODE_PATH).forward(request, response);
            return;
        }

        if (url.getRedirectTime() != null && url.getRedirectTime() > 0) {
            request.setAttribute("link", url.getLink());
            request.setAttribute("redirect_time", url.getRedirectTime());
            request.setAttribute("redirect_message", url.getRedirectMessage());
            request.getRequestDispatcher(REDIRECT_PATH).forward(request, response);
            return;
        }
        response.sendRedirect(url.getLink());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String link = request.getParameter("link");
        String uid = request.getParameter("uid");
        if (!uid.isEmpty()) {
            uid = URLEncoder.encode(uid, StandardCharsets.UTF_8.name());
        }
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
        Integer userId = authService.getUserId(request);
        UrlRequest urlRequest = UrlRequest.builder()
                .uid(uid)
                .link(link)
                .passcode(passcode)
                .redirectTime(redirectTime)
                .redirectMessage(redirectMessage)
                .expirationTime(expiratonTime)
                .userId(redirectTime)
                .userId(userId)
                .build();
        Url addedUrl = urlService.addUrl(urlRequest);
        if (addedUrl != null) {
            request.setAttribute("url", addedUrl.getUid());
        } else {
            request.setAttribute("url", "error");
        }
        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

}
