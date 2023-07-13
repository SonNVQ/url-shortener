package Services.Impl;

import Constants.CommonConstant;
import Constants.Regex;
import DAL.UrlDAO;
import DAL.UserDAO;
import Models.Url;
import Models.User;
import Services.UserService;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author nguyenson
 */
@Default
public class UserServiceImpl implements UserService {

    @Inject
    private UrlDAO urlDAO;

    @Inject
    private UserDAO userDAO;

    public UserServiceImpl() {
    }

    @Override
    public List<Url> getUrlsPaging(Integer userId, Integer size, Integer page) {
        if (userId == null) {
            return null;
        }
        if (size == null || size <= 0 || size > CommonConstant.PAGINATION_MAX_PAGE_SIZE) {
            size = CommonConstant.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        if (page == null || page < 0) {
            page = 1;
        }
        return urlDAO.getUrlsPaging(userId, size, page);
    }

    @Override
    public List<Url> searchUrlPaging(Integer userId, String searchField, String searchText,
            Date searchFromDate, Date searchToDate, Integer size, Integer page) {
        if (userId == null) {
            return null;
        }
        if (size == null || size <= 0 || size > CommonConstant.PAGINATION_MAX_PAGE_SIZE) {
            size = CommonConstant.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        if (page == null || page < 0) {
            page = 1;
        }
        if (searchFromDate == null) {
            searchFromDate = Date.valueOf(CommonConstant.MIN_DATE_STRING);
        }
        if (searchToDate == null) {
            searchToDate = Date.valueOf(CommonConstant.MAX_DATE_STRING);
        }
        return urlDAO.searchUrlPaging(userId, searchField, searchText, searchFromDate, searchToDate, size, page);
    }

    @Override
    public int countNumberOfLinkPages(Integer userId, Integer size) {
        if (userId == null) {
            return 0;
        }
        if (size == null || size <= 0 || size > CommonConstant.PAGINATION_MAX_PAGE_SIZE) {
            size = CommonConstant.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        int rowNumber = urlDAO.countNumberOfRows(userId);
        if (rowNumber == 0) {
            return 0;
        }
        Double totalDouble = Math.ceil(rowNumber * 1.0f / size);
        return totalDouble.intValue();
    }

    @Override
    public int countNumberOfSearchingPage(Integer userId, String searchField, String searchText, Date searchFromDate, Date searchToDate, Integer size) {
        if (userId == null) {
            return 0;
        }
        if (size == null || size <= 0 || size > CommonConstant.PAGINATION_MAX_PAGE_SIZE) {
            size = CommonConstant.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        if (searchFromDate == null) {
            searchFromDate = Date.valueOf(CommonConstant.MIN_DATE_STRING);
        }
        if (searchToDate == null) {
            searchToDate = Date.valueOf(CommonConstant.MAX_DATE_STRING);
        }
        int rowNumber = urlDAO.countNumberOfSearchingRows(userId, searchField, searchText, searchFromDate, searchToDate);
        if (rowNumber == 0) {
            return 0;
        }
        Double totalDouble = Math.ceil(rowNumber * 1.0f / size);
        return totalDouble.intValue();
    }

    @Override
    public User updateUser(User user) {
        if (!user.getLastName().matches(Regex.NON_EMPTY_STRING_CHECK)) {
            throw new IllegalArgumentException("LASTNAME_IS_EMPTY");
        }
        User updatedUser = userDAO.updateUser(user);
        if (updatedUser == null) {
            return null;
        }
        return updatedUser;
    }

    @Override
    public User getUser(Integer id) {
        if (id == null) {
            return null;
        }
        return userDAO.getUserById(id);
    }

    @Override
    public boolean changePassword(int id, String newPassword) {
        return userDAO.changePassword(id, newPassword);
    }

}
