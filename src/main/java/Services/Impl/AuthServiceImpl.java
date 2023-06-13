package Services.Impl;

import Constants.Regex;
import DAO.Impl.RoleDAOImpl;
import DAO.Impl.UserDAOImpl;
import DAO.RoleDAO;
import DAO.UserDAO;
import Models.Role;
import Models.User;
import Services.AuthService;
import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;

/**
 *
 * @author nguyenson
 */
public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final BcryptFunction bcrypt;

    public AuthServiceImpl() {
        userDAO = new UserDAOImpl();
        roleDAO = new RoleDAOImpl();
        bcrypt = BcryptFunction.getInstance(Bcrypt.A, 10);
    }

    @Override
    public User login(String username, String password) throws IllegalArgumentException {
        if (!username.matches(Regex.USERNAME_CHECK)) {
            throw new IllegalArgumentException("USERNAME_FORMAT_INVALID");
        }
        if (!password.matches(Regex.PASSWORD_CHECK)) {
            throw new IllegalArgumentException("PASSWORD_FORMAT_INVALID");
        }
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            return null;
        }
        boolean verified = Password.check(password, user.getPassword()).with(bcrypt);
        if (!verified) {
            return null;
        }
        return user;
    }

    @Override
    public User register(User user) throws IllegalArgumentException {
        String isValidUser = validateUserInfo(user);
        if (!isValidUser.equals("OK")) {
            throw new IllegalArgumentException(isValidUser);
        }
        User existedUserByUsername = userDAO.getUserByUsername(user.getUsername());
        if (existedUserByUsername != null) {
            throw new IllegalArgumentException("USERNAME_IS_EXISTED");
        }
        User existedUserByEmail = userDAO.getUserByEmail(user.getEmail());
        if (existedUserByEmail != null) {
            throw new IllegalArgumentException("EMAIL_IS_EXISTED");
        }
        User existedUserByGoogleEmail = userDAO.getUserByGoogleEmail(user.getGoogleEmail());
        if (existedUserByGoogleEmail != null) {
            throw new IllegalArgumentException("EMAIL_IS_EXISTED");
        }
        String hashedPassword = Password.hash(user.getPassword()).with(bcrypt).getResult();
        user.setPassword(hashedPassword);
        User addedUser = userDAO.addUser(user);
        if (addedUser == null) {
            return null;
        }
        User addedUserWithRole = userDAO.addUserRole(addedUser, Role.USER);
        if (addedUserWithRole == null) {
            return null;
        }
        return addedUserWithRole;
    }

    private String validateUserInfo(User user) {
        if (user.getUsername() == null || !user.getUsername().matches(Regex.USERNAME_CHECK)) {
            return "USERNAME_FORMAT_INVALID";
        }
        if (!user.getPassword().matches(Regex.PASSWORD_CHECK)) {
            return "PASSWORD_FORMAT_INVALID";
        }
        if (!user.getEmail().matches(Regex.EMAIL_CHECK)) {
            return "EMAIL_FORMAT_INVALID";
        }
        if (!user.getLastName().matches(Regex.NON_EMPTY_STRING_CHECK)) {
            return "LASTNAME_IS_EMPTY";
        }
        return "OK";
    }

    @Override
    public HashSet<Role> getUserRoles(User user) {
        HashSet<Role> roles = userDAO.getRoles(user);
        if (roles == null) {
            try {
                roleDAO.addRole(Role.USER);
                userDAO.addUserRole(user, Role.USER);
                roles = userDAO.getRoles(user);
            } catch (Exception ex) {
                throw new InternalError("CANNOT_ADD_ROLE");
            }
        }
        return roles;
    }

    @Override
    public boolean checkRole(HttpServletRequest request, Role role) {
        HttpSession session = request.getSession();
        if (session == null) {
            return false;
        }
        HashSet<Role> roles = (HashSet<Role>) session.getAttribute("roles");
        return roles.contains(role);
    }

    @Override
    public boolean isUser(HttpServletRequest request) {
        return checkRole(request, Role.USER);
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        return checkRole(request, Role.ADMIN);
    }

}
