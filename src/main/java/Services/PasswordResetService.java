package Services;

import Models.PasswordResetToken;
import Models.User;

/**
 *
 * @author nguyenson
 */
public interface PasswordResetService {

    PasswordResetToken sendToken(User user); //return email

    PasswordResetToken getToken(String id);

    Boolean deleteToken(String id);
}
