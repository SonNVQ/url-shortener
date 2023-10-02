package com.urlshortener.Controllers.Admin;

import com.urlshortener.Services.UrlService;
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
@WebServlet(name = "BanUrl", urlPatterns = {"/admin/links/ban"})
public class BanUrl extends HttpServlet {

    @Inject
    private UrlService urlService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));

        urlService.banUrl(id);

        response.sendRedirect("/admin/links/search");
    }

}
