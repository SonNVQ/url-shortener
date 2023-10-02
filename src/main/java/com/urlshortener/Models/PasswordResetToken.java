package com.urlshortener.Models;

import java.sql.Time;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author nguyenson
 */
@Data
@Builder
@ToString
public class PasswordResetToken {

    private String id;
    private int userId;
    private LocalDateTime createdTime;
    private LocalDateTime expirationTime;
    
}
