package com.urlshortener.DAL;

import com.urlshortener.Models.Role;
import com.urlshortener.Models.User;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenson
 */
public interface UserDAO {

    User addUser(User user);

    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByGoogleEmail(String email);
    
    boolean isUsernameExisted(String username);
    
    boolean isEmailExisted(String email);

    User updateUser(User user);

    User deleteUser(User user);

    User addUserRole(User user, Role role);

    HashSet<Role> getRoles(User user);

    User deleteUserRole(User user, Role role);
    
    List<User> searchAllUsersPaging(String searchField, String searchText, int size, int page);
    
    int countAllNumberOfSearchingRows(String searchField, String searchText);
    
    boolean changePassword(int id, String newPassword);

}
