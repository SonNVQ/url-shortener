package Services.Impl;

import Models.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;

/**
 *
 * @author nguyenson
 */
public class RoleService {

    public static boolean checkRole(HttpServletRequest request, Role role) {
        HttpSession session = request.getSession();
        if (session == null) {
            return false;
        }
        HashSet<Role> roles = (HashSet<Role>) session.getAttribute("roles");
        return roles.contains(role);
    }

    public static boolean isGuest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return !(session != null && session.getAttribute("user") != null);
    }

    public static boolean isUser(HttpServletRequest request) {
        return checkRole(request, Role.USER);
    }

    public static boolean isAdmin(HttpServletRequest request) {
        return checkRole(request, Role.ADMIN);
    }
}
