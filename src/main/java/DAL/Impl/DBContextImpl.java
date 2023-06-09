package DAL.Impl;

import DAL.DBContext;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Default;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Alternative
public class DBContextImpl implements DBContext {

//    @Resource(name = "jdbc/mssql")
//    private DataSource dataSource;
//
//    public DBContextImpl() {
//        try {
//            Context ctx = new InitialContext();
//            dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/mssql");
//        } catch (NamingException ex) {
//            Logger.getLogger(DBContextImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    @Override
//    public Connection getConnection() {
//        try {
//            Connection connection = dataSource.getConnection();
//            return connection;
//        } catch (SQLException ex) {
//            Logger.getLogger(DBContextImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//    
    @Override
    public Connection getConnection() {
        try {
            String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName + ";encrypt=false;trustServerCertificate=false;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, userID, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContextImplOld.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*Insert your other code right after this comment
    Change/update information of your database connection, DO NOT change name of instance variables in this class*/
    private final String serverName = "localhost";
    private final String dbName = "url_shortener";
    private final String portNumber = "1433";
    private final String userID = "sa";
    private final String password = "sa";

}
