package com.urlshortener.Services;

import com.urlshortener.Models.Email;

/**
 *
 * @author nguyenson
 */
public interface EmailService {

    Email sendMail(Email email);

}
