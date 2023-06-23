package DBTest;

import DAL.Impl.UserDAOImpl;
import DAL.UserDAO;
import Models.User;

/**
 *
 * @author nguyenson
 */
public class Test {
    
    public static void main(String[] args) {
        UserDAO userDAO2 = new UserDAOImpl();
        User test = userDAO2.getUserByGoogleEmail("sonqt123456@gmail.com");
        System.out.println(test);
                
    }
}
