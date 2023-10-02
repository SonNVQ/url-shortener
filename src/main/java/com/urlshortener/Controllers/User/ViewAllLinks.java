package com.urlshortener.Controllers.User;

import com.urlshortener.Models.Url;
import com.urlshortener.Services.AuthService;
import com.urlshortener.Services.UserService;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "LinkUser", urlPatterns = {"/links"})
public class ViewAllLinks extends HttpServlet {

    private static final String VIEW_PATH = "/user/links.jsp";

    @Inject
    private AuthService authService;

    @Inject
    private UserService userService;

    public ViewAllLinks() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sizeString = request.getParameter("size");
        String pageString = request.getParameter("page");
        Integer size, page;
        if (sizeString != null) {
            size = Integer.valueOf(sizeString);
        } else {
            size = 10;
        }
        if (pageString != null) {
            page = Integer.valueOf(pageString);
        } else {
            page = 1;
        }
        Integer userId = authService.getUserId(request);
        List<Url> urls = userService.getUrlsPaging(userId, size, page);
        int total = userService.countNumberOfLinkPages(userId, size);
        int start = Math.max(1, page - 2);
        int end = Math.min(total, page + 2);
        if (page < 3) {
            end = total < 5 ? total : 5;
        } else if (page > end - 2) {
            int tempStart = total - 4;
            start = tempStart > 0 ? tempStart : 1;
        }
        request.setAttribute("urls", urls);
        request.setAttribute("size", size);
        request.setAttribute("page", page);
        request.setAttribute("start", start);
        request.setAttribute("end", end);
        request.setAttribute("total", total);
        request.getRequestDispatcher(VIEW_PATH).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
