package com.urlshortener.Listeners;

import com.urlshortener.DAL.RoleDAO;
import com.urlshortener.Models.Role;
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

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        insertRoleToDatabase();
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

    private void addContextInitParam(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setInitParameter("FULL_DOMAIN", DotEnv.get("FULL_DOMAIN"));
        servletContext.setInitParameter("SHORT_DOMAIN", DotEnv.get("SHORT_DOMAIN"));
        servletContext.setInitParameter("GOOGLE_CLIENT_ID", DotEnv.get("GOOGLE_CLIENT_ID"));
    }
}
