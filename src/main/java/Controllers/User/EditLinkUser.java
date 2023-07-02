package Controllers.User;

import DTO.UrlRequest;
import Models.Url;
import Services.AuthService;
import Services.Impl.AuthServiceImpl;
import Services.Impl.UrlServiceImpl;
import Services.Impl.UserServiceImpl;
import Services.UrlService;
import Services.UserService;
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
public class EditLinkUser extends HttpServlet {

    private static final String FORM_PATH = "/user/link-form.jsp";

    private final AuthService authService;
    private final UserService userService;
    private final UrlService urlService;

    public EditLinkUser() {
        this.authService = new AuthServiceImpl();
        this.userService = new UserServiceImpl();
        this.urlService = new UrlServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uid = request.getParameter("uid");
        if (uid == null || uid.isEmpty()) {
            response.sendRedirect("links");
            return;
        }
        int userId = authService.getUserId(request);
        Url url = urlService.getUrl(uid);
        if (url.getUserId() != userId) {
            response.sendRedirect("links");
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
            response.sendRedirect("links");
            return;
        }
        int user_id = authService.getUserId(request);
        Url url = urlService.getUrl(uid);
        if (url.getUserId() != user_id) {
            response.sendRedirect("links");
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
        Integer userId = authService.getUserId(request);

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
