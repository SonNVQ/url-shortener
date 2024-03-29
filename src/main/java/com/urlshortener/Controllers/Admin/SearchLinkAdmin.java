package com.urlshortener.Controllers.Admin;

import com.urlshortener.Models.Url;
import com.urlshortener.Services.AdminService;
import com.urlshortener.Utils.Constants;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "SearchLinkAdmin", urlPatterns = {"/admin/links/search"})
public class SearchLinkAdmin extends HttpServlet {

    private static final String FORM_PATH = "/user/search-form.jsp";

    @Inject
    private AdminService adminService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher formDispatcher = request.getRequestDispatcher(FORM_PATH);

        String searchField = request.getParameter("searchField");
        if (searchField != null) {
            switch (searchField.trim()) {
                case "uid":
                case "link":
                case "title":
                    break;
                default:
                    request.setAttribute("FIELD_IS_INVALID", true);
                    formDispatcher.forward(request, response);
                    return;
            }
        } else {
            searchField = "title";
        }

        String searchText = request.getParameter("searchText");

        String searchFrom = request.getParameter("searchFrom");
        String searchTo = request.getParameter("searchTo");
        Date searchFromDate = null, searchToDate = null;
        String datePattern = "dd/MM/yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        if (searchFrom != null && !searchFrom.isEmpty()) {
            searchFromDate = Date.valueOf(LocalDate.parse(searchFrom, dateTimeFormatter));
        }
        if (searchTo != null && !searchTo.isEmpty()) {
            searchToDate = Date.valueOf(LocalDate.parse(searchTo, dateTimeFormatter));
        }

        String sizeString = request.getParameter("size");
        String pageString = request.getParameter("page");
        Integer size = null, page = null;
        if (sizeString != null) {
            size = Integer.valueOf(sizeString);
        } else {
            size = Constants.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        if (pageString != null) {
            page = Integer.valueOf(pageString);
        } else {
            page = 1;
        }

        //Send back old states
        request.setAttribute("searchField", searchField);
        request.setAttribute("searchText", searchText);
        request.setAttribute("searchFrom", searchFrom);
        request.setAttribute("searchTo", searchTo);

        List<Url> urls = adminService.searchAllUrlPaging(searchField, searchText, searchFromDate, searchToDate, size, page);
        int total = adminService.countAllNumberOfSearchingPage(searchField, searchText, searchFromDate, searchToDate, size);
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

        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
