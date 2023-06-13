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
    private String link;
    private String title;
    private String passcode;
    private int redirect_time;
    private String redirect_message;
    private LocalDateTime createdTime;
    private LocalDateTime expirationTime;
    private boolean is_banned;
    private String note;
    private String admin_note;
}
