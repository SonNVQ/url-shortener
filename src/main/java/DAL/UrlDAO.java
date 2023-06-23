package DAL;

import Models.Url;

/**
 *
 * @author nguyenson
 */
public interface UrlDAO {

    Url addUrl(Url url);

    Url getUrlById(int id);
    
    Url getUrlByUid(String uid);

    Url updateUrl(Url url);

    Boolean deleteUrlById(int id);

    Boolean isUrlIdAvailable(String uid);
}
