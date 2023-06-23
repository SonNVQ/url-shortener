package Utils;

import DAL.Impl.RoleDAOImpl;
import DAL.RoleDAO;
import Models.Role;

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
