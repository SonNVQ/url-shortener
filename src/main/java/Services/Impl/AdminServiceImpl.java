package Services.Impl;

import Constants.CommonConstant;
import DAL.UrlDAO;
import DAL.UserDAO;
import Models.Url;
import Models.User;
import Services.AdminService;
import jakarta.inject.Inject;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author nguyenson
 */
public class AdminServiceImpl implements AdminService {

    @Inject
    private UrlDAO urlDAO;

    @Inject
    private UserDAO userDAO;

    public AdminServiceImpl() {
    }

    @Override
    public List<Url> searchAllUrlPaging(String searchField, String searchText,
            Date searchFromDate, Date searchToDate, Integer size, Integer page) {
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
        return urlDAO.searchAllUrlPaging(searchField, searchText, searchFromDate, searchToDate, size, page);
    }

    @Override
    public int countAllNumberOfSearchingPage(String searchField, String searchText, Date searchFromDate, Date searchToDate, Integer size) {
        if (size == null || size <= 0 || size > CommonConstant.PAGINATION_MAX_PAGE_SIZE) {
            size = CommonConstant.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        if (searchFromDate == null) {
            searchFromDate = Date.valueOf(CommonConstant.MIN_DATE_STRING);
        }
        if (searchToDate == null) {
            searchToDate = Date.valueOf(CommonConstant.MAX_DATE_STRING);
        }
        int rowNumber = urlDAO.countAllNumberOfSearchingRows(searchField, searchText, searchFromDate, searchToDate);
        if (rowNumber == 0) {
            return 0;
        }
        Double totalDouble = Math.ceil(rowNumber * 1.0f / size);
        return totalDouble.intValue();
    }

    @Override
    public List<User> searchAllUserPaging(String searchField, String searchText, Integer size, Integer page) {
        if (size == null || size <= 0 || size > CommonConstant.PAGINATION_MAX_PAGE_SIZE) {
            size = CommonConstant.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        if (page == null || page < 0) {
            page = 1;
        }
        return userDAO.searchAllUsersPaging(searchField, searchText, size, page);
    }

    @Override
    public int countAllNumberOfUserSearchingPage(String searchField, String searchText, Integer size) {
        if (size == null || size <= 0 || size > CommonConstant.PAGINATION_MAX_PAGE_SIZE) {
            size = CommonConstant.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        int rowNumber = userDAO.countAllNumberOfSearchingRows(searchField, searchText);
        if (rowNumber == 0) {
            return 0;
        }
        Double totalDouble = Math.ceil(rowNumber * 1.0f / size);
        return totalDouble.intValue();
    }

}
