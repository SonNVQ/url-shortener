package Services.Impl;

import Constants.Regex;
import DAO.Impl.UserDAOImpl;
import DAO.UserDAO;
import Models.Role;
import Models.User;
import Services.AuthService;
import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

/**
 *
 * @author nguyenson
 */
public class AuthServiceImpl implements AuthService {

    UserDAO userDAO;
    BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 10);

    public AuthServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    @Override
    public User login(String username, String password) throws IllegalArgumentException {
        if (!username.matches(Regex.USERNAME_CHECK)) {
            throw new IllegalArgumentException("Username format is invalid");
        }
        if (!password.matches(Regex.PASSWORD_CHECK)) {
            throw new IllegalArgumentException("Password format is invalid");
        }
        String hashedPassword = Password.hash(password).with(bcrypt).getResult();
        User user = userDAO.login(username, hashedPassword);
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public User register(User user) throws IllegalArgumentException {
        String isValidUser = validateUserInfo(user);
        if (!isValidUser.equals("OK")) {
            throw new IllegalArgumentException(isValidUser);
        }
        User existedUserByEmail = userDAO.getUserByUsername(user.getUsername());
        if (existedUserByEmail != null) {
            throw new IllegalArgumentException("USERNAME_IS_EXISTED");
        }
        User existedUserByGoogleEmail = userDAO.getUserByGoogleEmail(user.getGoogleEmail());
        if (existedUserByGoogleEmail != null) {
            throw new IllegalArgumentException("USERNAME_IS_EXISTED");
        }
        User addedUser = userDAO.addUser(user);
        if (addedUser != null) {
            throw new IllegalArgumentException("ADDED_FAIL");
        }
        User addedUserWithRole = userDAO.addUserRole(addedUser, Role.USER);
        if (addedUserWithRole != null) {
            return addedUserWithRole;
        }
        return null;
    }

    private String validateUserInfo(User user) {
        if (!user.getUsername().matches(Regex.USERNAME_CHECK)) {
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

}
