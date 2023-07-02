package Services.Impl;

import Models.Role;
import Models.User;
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
        if (session == null || session.getAttribute("roles") == null) {
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

    public static String getUserLastName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("user") == null) {
            return null;
        }
        User user = (User) session.getAttribute("user");
        return user.getLastName();
    }
}
