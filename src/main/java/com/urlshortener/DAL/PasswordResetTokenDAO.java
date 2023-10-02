package com.urlshortener.DAL;

import com.urlshortener.Models.PasswordResetToken;

/**
 *
 * @author nguyenson
 */
public interface PasswordResetTokenDAO {
    PasswordResetToken addToken(PasswordResetToken token);
    
    PasswordResetToken getToken(String id);
    
    Boolean deleteTokenById(String id);
}
