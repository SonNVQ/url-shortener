package Controllers.Url;

import DTO.UrlRequest;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "HomepageUrl", urlPatterns = {"/"})
public class HomepageUrl extends HttpServlet {

    private static final String FORM_PATH = "/homepage.jsp";
    private static final String PASSCODE_PATH = "/url/passcode.jsp";
    private static final String REDIRECT_PATH = "/url/redirect.jsp";
    private static final String EXPIRED_PATH = "/url/expired.jsp";

    private final UrlService urlService;
    private final AuthService authService;

    public HomepageUrl() {
        this.urlService = new UrlServiceImpl();
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String uid = uri.substring(uri.lastIndexOf('/') + 1);
        Url url = urlService.getUrl(uid);
        System.out.println(url);
        if (url == null) {
            request.getRequestDispatcher(FORM_PATH).forward(request, response);
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
        //rs.getInt return int so redirectTime always an integer
        if (url.getRedirectTime() > 0) {
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
