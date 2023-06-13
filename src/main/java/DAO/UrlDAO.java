package DAO;

import Models.Url;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author nguyenson
 */
public interface UrlDAO {

    Url addUrl(Url url);

    Url getUrlById(int id);

    Url updateUrl(Url url);

    Url deleteUrl(Url url);

    Boolean isUrlIdAvailable(String uid);
}
