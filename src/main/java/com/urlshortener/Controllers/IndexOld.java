package com.urlshortener.Controllers;

import com.urlshortener.Services.AuthService;
import com.urlshortener.Services.Impl.AuthServiceImpl;
import com.urlshortener.Services.Impl.RoleService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author nguyenson
 */
//@WebServlet(name = "IndexOld", urlPatterns = {"/"})
public class IndexOld extends HttpServlet {

    private static final String VIEW_PATH = "homepage.jsp";

    AuthService authService;

    public IndexOld() {
        authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (RoleService.isGuest(request)) {
            request.setAttribute("role", "guest");
        }
        request.getRequestDispatcher(VIEW_PATH).forward(request, response);
        String s = "<div id=\"g_id_onload\"\n"
                + "                 data-client_id=\"205125337007-vp8gcc90umgim1krgh90e6lcafj12obf.apps.googleusercontent.com\"\n"
                + "                 data-context=\"signin\"\n"
                + "                 data-ux_mode=\"popup\"\n"
                + "                 data-login_uri=\"http://localhost:8088/url/auth/login-google\"\n"
                + "                 data-itp_support=\"true\">\n"
                + "            </div>";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
