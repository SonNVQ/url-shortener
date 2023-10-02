package com.urlshortener.Models;

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
public class Email {

    private String from;
    private String to;
    private String subject;
    private String html;
    
}
