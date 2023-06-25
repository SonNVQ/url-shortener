package Services.Impl;

import DAL.Impl.UrlDAOImpl;
import DAL.UrlDAO;
import DTO.UrlRequest;
import Models.Url;
import Services.UidService;
import Services.UrlService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author nguyenson
 */
public class UrlServiceImpl implements UrlService {

    private final UrlDAO urlDAO;
    private final UidService uidService;

    public UrlServiceImpl() {
        this.urlDAO = new UrlDAOImpl();
        this.uidService = new UidServiceImpl();
    }

    private Boolean isLinkValid(String link) {
        UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES);
        return urlValidator.isValid(link);
    }

    @Override
    public Url addUrl(UrlRequest urlRequest) {
        if (!isLinkValid(urlRequest.getLink())) {
            throw new IllegalArgumentException("LINK_IS_INVALID");
        }
        String uid = urlRequest.getUid();
        if (uid != null && !urlRequest.getUid().isEmpty() && !urlDAO.isUidAvailable(uid)) {
            throw new IllegalArgumentException("UID_IS_NOT_AVAILABLE");
        }
        String title = getLinkTitle(urlRequest.getLink());
        LocalDateTime createdTime = LocalDateTime.now();
        uid = uidService.generateUid();
        Url url = Url.builder()
                .uid(uid)
                .userId(urlRequest.getUserId())
                .link(urlRequest.getLink())
                .title(title)
                .passcode(urlRequest.getPasscode())
                .redirectTime(urlRequest.getRedirectTime())
                .redirectMessage(urlRequest.getRedirectMessage())
                .createdTime(createdTime)
                .expirationTime(urlRequest.getExpirationTime())
                .note(urlRequest.getNote())
                .build();
        Url addedUrl = urlDAO.addUrl(url);
        if (addedUrl == null) {
            throw new RuntimeException("ADDED_FAIL");
        }
        return addedUrl;
    }

    @Override
    public Url getUrl(int id) {
        return urlDAO.getUrlById(id);
    }

    @Override
    public Url getUrl(String uid) {
        return urlDAO.getUrlByUid(uid);
    }

    @Override
    public Url updateUrl(Url url) {
        return urlDAO.updateUrl(url);
    }

    @Override
    public Boolean deleteUrl(int id) {
        return urlDAO.deleteUrlById(id);
    }

    @Override
    public String getLinkTitle(String link) {
        try {
            //connect to the website
            Connection connection = Jsoup.connect(link);
            //specify user agent
            connection.userAgent("Mozilla/5.0");
            Document document = connection.get();
            return document.title();
        } catch (IOException ex) {
            Logger.getLogger(UrlServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
