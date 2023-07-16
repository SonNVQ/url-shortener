package Utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author nguyenson
 */
public class CookieUtils {

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; ++i) {
            if (cookieName.equals(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    public static void updateCookieValue(HttpServletRequest request, HttpServletResponse response, String cookieName, String updatedValue) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; ++i) {
            if (cookieName.equals(cookies[i].getName())) {
                cookies[i].setValue(updatedValue);
                response.addCookie(cookies[i]);
                return;
            }
        }
    }

    public static void deleteCookieByName(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; ++i) {
            if (cookieName.equals(cookies[i].getName())) {
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
                return;
            }
        }
    }
}
