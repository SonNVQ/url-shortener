package DAL;

import Models.Url;
import java.util.List;

/**
 *
 * @author nguyenson
 */
public interface UrlDAO {

    Url addUrl(Url url);

    Url getUrlById(int id);
    
    Url getUrlByUid(String uid);
    
    List<Url> getUrlsPaging(int userId, int size, int page);
    
    int countNumberOfRows(int userId);

    Url updateUrl(Url url);

    Boolean deleteUrlById(int id);

    Boolean isUidAvailable(String uid);
}
