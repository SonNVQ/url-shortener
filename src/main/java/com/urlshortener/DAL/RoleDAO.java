package com.urlshortener.DAL;

import com.urlshortener.Models.Role;

/**
 *
 * @author nguyenson
 */
public interface RoleDAO {

    Role addRole(Role role);

    Integer getRoleIdByRoleName(Role role);

    Role getRoleById(int id);
}
