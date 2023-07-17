package Services.Impl;

import Constants.CommonConstant;
import DAL.UrlDAO;
import DAL.UserDAO;
import Models.Email;
import Models.Role;
import Models.Url;
import Models.User;
import Services.AdminService;
import Services.EmailService;
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

    @Inject
    private EmailService emailService;

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

    @Override
    public Boolean setRoleAdmin(int id) {
        User user = userDAO.getUserById(id);
        if (user == null) {
            return false;
        }
        User updatedUser = userDAO.addUserRole(user, Role.ADMIN);
        if (updatedUser == null) {
            return false;
        }
        String content = "<div style=\"color: #262626;\">\n"
                + "    <h3 style=\"text-transform: uppercase; color: #3b71ca;\">oi.io.vn - Notification</h3>\n"
                + "    <h4 style=\"color: green;\">You are now admin of oi.io.vn!</h4>\n"
                + "    <h4>Contact <a href=\"mailto:nguyenson.admin@oi.io.vn\">nguyenson.admin@oi.io.vn</a> for more information!</h4>\n"
                + "</div>";
        Email email = Email.builder()
                .from("admin@oi.io.vn <admin@oi.io.vn>")
                .to(user.getEmail())
                .subject("You are now admin!")
                .html(content)
                .build();
        emailService.sendMail(email);
        return true;
    }

    @Override
    public Boolean removeRoleAdmin(int id) {
        User user = userDAO.getUserById(id);
        if (user == null) {
            return false;
        }
        User updatedUser = userDAO.deleteUserRole(user, Role.ADMIN);
        if (updatedUser == null) {
            return false;
        }
        String content = "<div style=\"color: #262626;\">\n"
                + "    <h3 style=\"text-transform: uppercase; color: #3b71ca;\">oi.io.vn - Notification</h3>\n"
                + "    <h4 style=\"color: red;\">You are no longer an admin of oi.io.vn!</h4>\n"
                + "    <h4>Contact <a href=\"mailto:nguyenson.admin@oi.io.vn\">nguyenson.admin@oi.io.vn</a> for more information!</h4>\n"
                + "</div>";
        Email email = Email.builder()
                .from("admin@oi.io.vn <admin@oi.io.vn>")
                .to(user.getEmail())
                .subject("You are now admin!")
                .html(content)
                .build();
        emailService.sendMail(email);
        return true;
    }

}
