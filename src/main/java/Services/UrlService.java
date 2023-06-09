package Services;

import DTO.UrlRequest;
import Models.Url;

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
    
}
