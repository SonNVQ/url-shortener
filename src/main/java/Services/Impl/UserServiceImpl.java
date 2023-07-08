package Services.Impl;

import DAL.Impl.UrlDAOImpl;
import DAL.UrlDAO;
import Models.Url;
import Services.UserService;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.util.List;

/**
 *
 * @author nguyenson
 */
@Default
public class UserServiceImpl implements UserService {

    @Inject
    private UrlDAO urlDAO;

    public UserServiceImpl() {
    }

    @Override
    public List<Url> getUrlsPaging(Integer userId, Integer size, Integer page) {
        if (userId == null) {
            return null;
        }
        if (size == null || size <= 0 || size > 100) {
            size = 10;
        }
        if (page == null || page < 0) {
            page = 1;
        }
        return urlDAO.getUrlsPaging(userId, size, page);
    }

    @Override
    public int countNumberOfLinkPages(Integer userId, Integer size) {
        if (userId == null) {
            return 0;
        }
        if (size == null || size <= 0 || size > 100) {
            size = 10;
        }
        int rowNumber = urlDAO.countNumberOfRows(userId);
        if (rowNumber == 0) {
            return 0;
        }
        Double totalDouble = Math.ceil(rowNumber * 1.0f / size);
        return totalDouble.intValue();
    }

}