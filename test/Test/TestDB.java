package Test;

import java.sql.Connection;

import DAO.DBContext;
import DAO.DBContextImpl;


/**
 *
 * @author nguyenson
 */
public class TestDB {
    public static void main(String[] args) {
        DBContext dBContext = new DBContextImpl();
        try {
            Connection connection = dBContext.getConnection();
            System.out.println(connection);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
