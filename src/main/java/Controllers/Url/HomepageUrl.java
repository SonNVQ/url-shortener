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

/**
 *
 * @author nguyenson
 */
@WebServlet(name = "HomepageUrl", urlPatterns = {"/"})
public class HomepageUrl extends HttpServlet {
    
    private static final String FORM_PATH = "/homepage.jsp";
    private static final String PASSCODE_PATH = "/url/passcode.jsp";
    
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
        if (url == null) {
            request.getRequestDispatcher(FORM_PATH).forward(request, response);
            return;
        }
        request.setAttribute("uid", uid);
        if (url.getPasscode() != null) {
            request.getRequestDispatcher(PASSCODE_PATH).forward(request, response);
            return;
        }
        response.sendRedirect(url.getLink());
//        System.out.println(request.getRequestURI());
//
//        request.setAttribute("url", uid);
//        request.getRequestDispatcher(FORM_PATH).forward(request, response);
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
