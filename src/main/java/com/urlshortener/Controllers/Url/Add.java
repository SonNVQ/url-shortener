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
import java.time.LocalDateTime;

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "AddUrl", urlPatterns = {"/url/add"})
public class Add extends HttpServlet {

    private static final String FORM_PATH = "url-form.jsp";

    @Inject
    private UrlService urlService;

    @Inject
    private AuthService authService;

    public Add() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String link = request.getParameter("link");
        String uid = request.getParameter("uid");
        String passcode = request.getParameter("passcode");
        String redirectTimeString = request.getParameter("redirect-time");
        Integer redirectTime = null;
        if (!redirectTimeString.isEmpty()) {
            redirectTime = Integer.parseInt(redirectTimeString);
        }
        String redirectMessage = request.getParameter("redirect-message");
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
