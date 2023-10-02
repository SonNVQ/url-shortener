package com.urlshortener.Services;

import com.urlshortener.Models.Url;
import com.urlshortener.Models.User;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author nguyenson
 */
public interface AdminService {

    List<Url> searchAllUrlPaging(String searchField, String searchText,
            Date searchFromDate, Date searchToDate, Integer size, Integer page);

    int countAllNumberOfSearchingPage(String searchField, String searchText,
            Date searchFromDate, Date searchToDate, Integer size);
    
    List<User> searchAllUserPaging(String searchField, String searchText, Integer size, Integer page);

    int countAllNumberOfUserSearchingPage(String searchField, String searchText, Integer size);

    Boolean setRoleAdmin(int id);
    
    Boolean removeRoleAdmin(int id);
    
}
