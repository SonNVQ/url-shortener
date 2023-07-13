package Services;

import Models.GoogleUser;
import Models.Role;
import Models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashSet;

/**
 *
 * @author nguyenson
 */
public interface AuthService {

    User login(HttpServletRequest request) throws IllegalArgumentException;

    User register(User user) throws IllegalArgumentException;

    User getUser(HttpServletRequest request);

    Integer getUserId(HttpServletRequest request);

    boolean checkRole(HttpServletRequest request, Role role);

    boolean isGuest(HttpServletRequest request);

    boolean isUser(HttpServletRequest request);

    boolean isAdmin(HttpServletRequest request);

    User googleLogin(HttpServletRequest request);

}
