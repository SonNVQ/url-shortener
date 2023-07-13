package DAL;

import Models.Url;
import java.sql.Date;
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

    List<Url> searchUrlPaging(int userId, String searchField, String searchText,
            Date searchFromDate, Date searchToDate, int size, int page);

    int countNumberOfRows(int userId);

    int countNumberOfSearchingRows(int userId, String searchField, String searchText,
            Date searchFromDate, Date searchToDate);

    List<Url> getAllUrlsPaging(int size, int page);

    List<Url> searchAllUrlPaging(String searchField, String searchText,
            Date searchFromDate, Date searchToDate, int size, int page);

    int countAllNumberOfRows();

    int countAllNumberOfSearchingRows(String searchField, String searchText,
            Date searchFromDate, Date searchToDate);

    Url updateUrl(Url url);

    Boolean deleteUrlById(int id);

    Boolean isUidAvailable(String uid);
    
    Boolean banUrl(int id);
    
    Boolean unbanUrl(int id);
}
