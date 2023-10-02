package com.urlshortener.Utils;

import com.urlshortener.DAL.Impl.RoleDAOImpl;
import com.urlshortener.DAL.RoleDAO;
import com.urlshortener.Models.Role;

/**
 *
 * @author nguyenson
 */
public class InitRoles {

    public static void main(String[] args) {
        RoleDAO roleDAO = new RoleDAOImpl();
        try {
            for (Role role : Role.values()) {
                roleDAO.addRole(role);
            }
        } catch (Exception e) {
        }
    }
}
