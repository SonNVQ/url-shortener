package com.urlshortener.Services;

import com.urlshortener.DTO.UrlRequest;
import com.urlshortener.Models.Url;

/**
 *
 * @author nguyenson
 */
public interface UrlService {
    Url addUrl(UrlRequest urlRequest);
    
    Url getUrl(int id);
    
    Url getUrl(String uid);
    
    Url updateUrl(Url url);
    
    Boolean deleteUrl(int id);
    
    String getLinkTitle(String link);
    
    Boolean banUrl(int id);
    
    Boolean unbanUrl(int id);
    
}
