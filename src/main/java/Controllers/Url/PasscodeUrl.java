package Controllers.Url;

import Models.Url;
import Services.AuthService;
import Services.Impl.AuthServiceImpl;
import Services.Impl.UrlServiceImpl;
import Services.UrlService;
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
@WebServlet(name = "PasscodeUrl", urlPatterns = {"/passcode"})
public class PasscodeUrl extends HttpServlet {

    private static final String FORM_PATH = "/url/passcode.jsp";
    private static final String REDIRECT_PATH = "/url/redirect.jsp";

    private final UrlService urlService;
    private final AuthService authService;

    public PasscodeUrl() {
        this.urlService = new UrlServiceImpl();
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(FORM_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String passcode = request.getParameter("passcode");
        if (uid == null) {
            response.sendRedirect("/");
            return;
        }
        Url url = urlService.getUrl(uid);
        request.setAttribute("uid", uid);
        if (passcode == null || !passcode.equals(url.getPasscode())) {
            request.setAttribute("wrong_passcode", true);
            request.getRequestDispatcher(FORM_PATH).forward(request, response);
            return;
        }
        if (url.getRedirectTime() != null) {
            request.setAttribute("link", url.getLink());
            request.setAttribute("redirect_time", url.getRedirectTime());
            request.setAttribute("redirect_message", url.getRedirectMessage());
            request.getRequestDispatcher(REDIRECT_PATH).forward(request, response);
            return;
        }
        response.sendRedirect(url.getLink());
    }

}
