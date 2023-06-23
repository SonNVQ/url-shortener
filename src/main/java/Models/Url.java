package Models;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author nguyenson
 */
@Data
@Builder
public class Url {

    private int id;
    private String uid;
    private int userId;
    private String link;
    private String title;
    private String passcode;
    private int redirectTime;
    private String redirectMessage;
    private LocalDateTime createdTime;
    private LocalDateTime expirationTime;
    private boolean isBanned;
    private String note;
    private String adminNote;
}
