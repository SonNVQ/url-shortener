package com.urlshortener.Services.Impl;

import com.urlshortener.DAL.PasswordResetTokenDAO;
import com.urlshortener.Models.Email;
import com.urlshortener.Models.PasswordResetToken;
import com.urlshortener.Models.User;
import com.urlshortener.Services.AuthService;
import com.urlshortener.Services.EmailService;
import com.urlshortener.Services.PasswordResetService;
import com.urlshortener.Utils.Constants;
import com.urlshortener.Utils.UUIDUtils;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.time.LocalDateTime;

/**
 *
 * @author nguyenson
 */
@Default
public class PasswordResetServiceImpl implements PasswordResetService {
    
    @Inject
    private EmailService emailService;
    
    @Inject
    private AuthService authService;
    
    @Inject
    private PasswordResetTokenDAO passwordResetTokenDAO;
    
    @Override
    public PasswordResetToken sendToken(User user) {
        String newTokenId = UUIDUtils.createRandomUUID();
        LocalDateTime createdTime = LocalDateTime.now();
        LocalDateTime expirationTime = createdTime.plusMinutes(Constants.PASSWORD_RESET_TOKEN_LIVE_TIME_IN_MINUTE);
        PasswordResetToken token = PasswordResetToken.builder()
                .id(newTokenId)
                .userId(user.getId())
                .createdTime(createdTime)
                .expirationTime(expirationTime)
                .build();
        PasswordResetToken addedToken = passwordResetTokenDAO.addToken(token);
        if (addedToken == null) {
            return null;
        }
        String link = Constants.FULL_DOMAIN + "/auth/resetPassword?id=" + newTokenId;
        String content = "<div style=\"color: #262626;\">\n"
                + "    <h2 style=\"text-transform: uppercase; color: #3b71ca;\">oi.io.vn - Reset password</h2>\n"
                + "    <h3>You are going to reset password for account " + user.getUsername() + ".</h3>\n"
                + "    <h3>Please follow this <a href=\"" + link + "\">link</a> to reset your password!</h3>\n"
                + "    <h4 style=\"color: red;\">*Important: Do not reveal this link, or your account will be stolen.</h4>\n"
                + "</div>";
        Email resetEmail = Email.builder()
                .from("account@oi.io.vn <account@oi.io.vn>")
                .to(user.getEmail())
                .subject("Reset password")
                .html(content)
                .build();
        emailService.sendMail(resetEmail);
        return addedToken;
    }
    
    @Override
    public PasswordResetToken getToken(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return passwordResetTokenDAO.getToken(id);
    }
    
    @Override
    public Boolean deleteToken(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return passwordResetTokenDAO.deleteTokenById(id);
    }
    
}
