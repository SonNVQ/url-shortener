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

    private Integer id;
    private String uid;
    private Integer userId;
    private String link;
    private String title;
    private String passcode;
    private Integer redirectTime;
    private String redirectMessage;
    private LocalDateTime createdTime;
    private LocalDateTime expirationTime;
    private Boolean isBanned;
    private String note;
    private String adminNote;
}
