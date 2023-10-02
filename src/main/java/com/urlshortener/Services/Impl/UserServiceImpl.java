package com.urlshortener.Services.Impl;

import com.urlshortener.DAL.UrlDAO;
import com.urlshortener.DAL.UserDAO;
import com.urlshortener.Models.Email;
import com.urlshortener.Models.Url;
import com.urlshortener.Models.User;
import com.urlshortener.Services.EmailService;
import com.urlshortener.Services.UserService;
import com.urlshortener.Utils.Constants;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

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

    @Inject
    private EmailService emailService;

    public UserServiceImpl() {
    }

    @Override
    public List<Url> getUrlsPaging(Integer userId, Integer size, Integer page) {
        if (userId == null) {
            return null;
        }
        if (size == null || size <= 0 || size > Constants.PAGINATION_MAX_PAGE_SIZE) {
            size = Constants.PAGINATION_DEFAULT_PAGE_SIZE;
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
        if (size == null || size <= 0 || size > Constants.PAGINATION_MAX_PAGE_SIZE) {
            size = Constants.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        if (page == null || page < 0) {
            page = 1;
        }
        if (searchFromDate == null) {
            searchFromDate = Date.valueOf(Constants.MIN_DATE_STRING);
        }
        if (searchToDate == null) {
            searchToDate = Date.valueOf(Constants.MAX_DATE_STRING);
        }
        return urlDAO.searchUrlPaging(userId, searchField, searchText, searchFromDate, searchToDate, size, page);
    }

    @Override
    public int countNumberOfLinkPages(Integer userId, Integer size) {
        if (userId == null) {
            return 0;
        }
        if (size == null || size <= 0 || size > Constants.PAGINATION_MAX_PAGE_SIZE) {
            size = Constants.PAGINATION_DEFAULT_PAGE_SIZE;
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
        if (size == null || size <= 0 || size > Constants.PAGINATION_MAX_PAGE_SIZE) {
            size = Constants.PAGINATION_DEFAULT_PAGE_SIZE;
        }
        if (searchFromDate == null) {
            searchFromDate = Date.valueOf(Constants.MIN_DATE_STRING);
        }
        if (searchToDate == null) {
            searchToDate = Date.valueOf(Constants.MAX_DATE_STRING);
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
        if (StringUtils.isBlank(user.getLastName())) {
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
    public User getUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }
        return userDAO.getUserByUsername(username);
    }

    @Override
    public boolean changePassword(int id, String newPassword) {
        User user = userDAO.getUserById(id);
        if (user == null) {
            return false;
        }
        boolean isPasswordChanged = userDAO.changePassword(id, newPassword);
        if (!isPasswordChanged) {
            return false;
        }
        String content = "<div style=\"color: #262626;\">\n"
                + "    <h3 style=\"text-transform: uppercase; color: #3b71ca;\">oi.io.vn - Notification</h3>\n"
                + "    <h4 style=\"color: gold;\">Your password has been changed at " + LocalDateTime.now().toString() + "!</h4>\n"
                + "    <h4>Contact: <a href=\"mailto:admin@oi.io.vn\">password@oi.io.vn</a></h4>\n"
                + "</div>";
        Email bannedEmail = Email.builder()
                .from("password@oi.io.vn <password@oi.io.vn>")
                .to(user.getEmail())
                .subject("Password changed notice")
                .html(content)
                .build();
        emailService.sendMail(bannedEmail);
        return true;
    }

}
