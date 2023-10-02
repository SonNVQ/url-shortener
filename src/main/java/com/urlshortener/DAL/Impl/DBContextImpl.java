package com.urlshortener.DAL.Impl;

import com.urlshortener.DAL.DBContext;
import com.urlshortener.Utils.DotEnv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContextImpl implements DBContext {

    @Override
    public Connection getConnection() {
        String connectionString = "jdbc:mysql://" + DotEnv.get("MYSQL_HOST")
                + "/" + DotEnv.get("MYSQL_DATABASE");
        String username = DotEnv.get("MYSQL_USERNAME");
        String password = DotEnv.get("MYSQL_PASSWORD");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(connectionString, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
