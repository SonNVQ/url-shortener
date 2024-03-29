package com.urlshortener.Services;

import com.urlshortener.Models.Url;
import com.urlshortener.Models.User;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author nguyenson
 */
public interface UserService {

    List<Url> getUrlsPaging(Integer userId, Integer size, Integer page);

    List<Url> searchUrlPaging(Integer userId, String searchField, String searchText,
            Date searchFromDate, Date searchToDate, Integer size, Integer page);

    int countNumberOfLinkPages(Integer userId, Integer size);
    
    int countNumberOfSearchingPage(Integer userId, String searchField, String searchText, Date searchFromDate, Date searchToDate, Integer size);

    User updateUser(User user);
    
    User getUser(Integer id);
    
    User getUserByUsername(String username);
    
    boolean changePassword(int id, String newPassword);

}
