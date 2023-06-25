package DTO;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author nguyenson
 */
@Data
@Builder
public class UrlRequest {

    private String uid;
    private String link;
    private String passcode;
    private Integer redirectTime;
    private String redirectMessage;
    private LocalDateTime expirationTime;
    private String note;
    private Integer userId;
    
}
