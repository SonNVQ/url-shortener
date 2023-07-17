package Utils;

import java.util.UUID;

/**
 *
 * @author nguyenson
 */
public class UUIDUtils {

    public static String createRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static void main(String[] args) {
        System.out.println(createRandomUUID());
    }
    
}
