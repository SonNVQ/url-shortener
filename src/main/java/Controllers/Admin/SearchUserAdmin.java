package Controllers.Admin;

import Constants.CommonConstant;
import Models.User;
import Services.AdminService;
import Services.AuthService;
import Services.Impl.RoleService;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
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
@WebServlet(name = "SearchUserAdmin", urlPatterns = {"/admin/users/search"})
public class SearchUserAdmin extends HttpServlet {
    
    private static final String FORM_PATH = "/admin/users.jsp";
    
    @Inject
    private AuthService authService;
    
    @Inject
    private AdminService adminService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!authService.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        RequestDispatcher formDispatcher = request.getRequestDispatcher(FORM_PATH);
        
        String searchField = request.getParameter("searchField");
        if (searchField != null) {
            switch (searchField.trim()) {
                case "email":
                case "username":
                case "name":
                    break;
                default:
                    request.setAttribute("FIELD_IS_INVALID", true);
                    formDispatcher.forward(request, response);
                    return;
            }
        } else {
            searchField = "name";
        }
        
        String searchText = request.getParameter("searchText");
        
        String sizeString = request.getParameter("size");
        String pageString = request.getParameter("page");
        Integer size = null, page = null;
        if (sizeString != null) {
            size = Integer.valueOf(sizeString);
        } else {
            size = CommonConstant.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        if (pageString != null) {
            page = Integer.valueOf(pageString);
        } else {
            page = 1;
        }

        //Send back old states
        request.setAttribute("searchField", searchField);
        request.setAttribute("searchText", searchText);
        
        List<User> users = adminService.searchAllUserPaging(searchField, searchText, size, page);
        int total = adminService.countAllNumberOfUserSearchingPage(searchField, searchText, size);
        int start = Math.max(1, page - 2);
        int end = Math.min(total, page + 2);
        if (page < 3) {
            end = total < 5 ? total : 5;
        } else if (page > end - 2) {
            int tempStart = total - 4;
            start = tempStart > 0 ? tempStart : 1;
        }
        request.setAttribute("users", users);
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
