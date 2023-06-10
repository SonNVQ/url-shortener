package Models;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author nguyenson
 */
@Data
@Builder
public class User {

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String googleEmail;
}
