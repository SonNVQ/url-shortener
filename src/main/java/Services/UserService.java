package Services;

import Models.Url;
import java.util.List;

/**
 *
 * @author nguyenson
 */
public interface UserService {

    List<Url> getUrlsPaging(Integer userId, Integer size, Integer page);

    int countNumberOfLinkPages(Integer userId, Integer size);

}
