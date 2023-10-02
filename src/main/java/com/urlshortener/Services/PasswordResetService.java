package com.urlshortener.Services;

import com.urlshortener.Models.PasswordResetToken;
import com.urlshortener.Models.User;

/**
 *
 * @author nguyenson
 */
public interface PasswordResetService {

    PasswordResetToken sendToken(User user); //return email

    PasswordResetToken getToken(String id);

    Boolean deleteToken(String id);
}
