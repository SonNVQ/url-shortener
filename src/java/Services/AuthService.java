package Services;

import Models.User;

/**
 *
 * @author nguyenson
 */
public interface AuthService {

    User login(String username, String password) throws IllegalArgumentException;

    User register(User user) throws IllegalArgumentException;
}
