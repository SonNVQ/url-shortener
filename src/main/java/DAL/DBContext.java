package DAL;

import java.sql.Connection;

/**
 *
 * @author nguyenson
 */
public interface DBContext {
    Connection getConnection();
}
