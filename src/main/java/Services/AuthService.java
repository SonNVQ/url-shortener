package Services;

import Models.GoogleUser;
import Models.Role;
import Models.User;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashSet;

/**
 *
 * @author nguyenson
 */
public interface AuthService {

    User login(HttpServletRequest request) throws IllegalArgumentException;

    User register(User user) throws IllegalArgumentException;
    
    Integer getUserId(HttpServletRequest request);

    boolean checkRole(HttpServletRequest request, Role role);

    boolean isGuest(HttpServletRequest request);

    boolean isUser(HttpServletRequest request);

    boolean isAdmin(HttpServletRequest request);

    User googleLogin(HttpServletRequest request);
}
