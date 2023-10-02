package com.urlshortener.Listeners;

import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import com.urlshortener.DAL.RoleDAO;
import com.urlshortener.DAL.UserDAO;
import com.urlshortener.Models.Role;
import com.urlshortener.Models.User;
import com.urlshortener.Services.AuthService;
import com.urlshortener.Services.UserService;
import com.urlshortener.Utils.DotEnv;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ConfigurationListener implements ServletContextListener {

    @Inject
    private RoleDAO roleDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private AuthService authService;

    @Inject
    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        insertRoleToDatabase();
        insertAdminAccount();
        addContextInitParam(sce);
    }

    private void insertRoleToDatabase() {
        for (Role role : Role.values()) {
            Integer roleId = roleDAO.getRoleIdByRoleName(role);
            if (roleId == null) {
                roleDAO.addRole(role);
            }
        }
    }

    private void insertAdminAccount() {
        if (userService.getUserByUsername("admin") != null) {
            return;
        }
        User user = User.builder()
                .username("admin")
                .email("admin@oi.io.vn")
                .firstName("00")
                .lastName("admin")
                .password("tempass@0000")
                .build();
        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.A, 10);
        String hashedPassword = Password.hash(user.getPassword()).with(bcrypt).getResult();
        user.setPassword(hashedPassword);
        User addedUser = userDAO.addUser(user);
        userDAO.addUserRole(addedUser, Role.USER);
        userDAO.addUserRole(addedUser, Role.ADMIN);
    }

    private void addContextInitParam(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setInitParameter("FULL_DOMAIN", DotEnv.get("FULL_DOMAIN"));
        servletContext.setInitParameter("SHORT_DOMAIN", DotEnv.get("SHORT_DOMAIN"));
        servletContext.setInitParameter("GOOGLE_CLIENT_ID", DotEnv.get("GOOGLE_CLIENT_ID"));
    }
}
