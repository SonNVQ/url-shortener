package Services;

import Models.Role;
import Models.User;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashSet;

/**
 *
 * @author nguyenson
 */
public interface AuthService {

    User login(String username, String password) throws IllegalArgumentException;

    User register(User user) throws IllegalArgumentException;
    
    HashSet<Role> getUserRoles(User user);
    
    boolean checkRole(HttpServletRequest request, Role role);
    
    boolean isUser(HttpServletRequest request);
    
    boolean isAdmin(HttpServletRequest request);
}
