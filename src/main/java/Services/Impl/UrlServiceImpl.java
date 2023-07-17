package Services.Impl;

import Constants.CommonConstant;
import DAL.UrlDAO;
import DAL.UserDAO;
import DTO.UrlRequest;
import Models.Email;
import Models.Url;
import Models.User;
import Services.EmailService;
import Services.UidService;
import Services.UrlService;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
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
@Default
public class UrlServiceImpl implements UrlService {

    @Inject
    private UrlDAO urlDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private UidService uidService;

    @Inject
    private EmailService emailService;

    public UrlServiceImpl() {
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
        if (uid == null || uid.isEmpty()) {
            uid = uidService.generateUid();
        }
        String title = getLinkTitle(urlRequest.getLink());
        LocalDateTime createdTime = LocalDateTime.now();
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

    @Override
    public Boolean banUrl(int id) {
        Url url = urlDAO.getUrlById(id);
        if (url == null) {
            return false;
        }
        Boolean isBanned = urlDAO.banUrl(id);
        if (!isBanned) {
            return false;
        }
        User user = userDAO.getUserById(url.getUserId());
        String link = CommonConstant.FULL_DOMAIN + "/" + url.getUid();
        String content = "<div style=\"color: #262626;\">\n"
                + "    <h3 style=\"text-transform: uppercase; color: #3b71ca;\">oi.io.vn - Notification</h3>\n"
                + "    <h4 style=\"color: red;\">Your link <a href=\"" + link + "\">" + link + "</a> has been banned by admin!</h4>\n"
                + "    <h4>Contact <a href=\"mailto:feedback@oi.io.vn\">feedback@oi.io.vn</a> for more information!</h4>\n"
                + "</div>";
        Email bannedEmail = Email.builder()
                .from("admin@oi.io.vn <admin@oi.io.vn>")
                .to(user.getEmail())
                .subject("Banned url notice")
                .html(content)
                .build();
        emailService.sendMail(bannedEmail);
        return true;
    }

    @Override
    public Boolean unbanUrl(int id) {
        Url url = urlDAO.getUrlById(id);
        if (url == null) {
            return false;
        }
        Boolean isBanned = urlDAO.unbanUrl(id);
        if (!isBanned) {
            return false;
        }
        User user = userDAO.getUserById(url.getUserId());
        String link = CommonConstant.FULL_DOMAIN + "/" + url.getUid();
        String content = "<div style=\"color: #262626;\">\n"
                + "    <h3 style=\"text-transform: uppercase; color: #3b71ca;\">oi.io.vn - Notification</h3>\n"
                + "    <h4 style=\"color: green;\">Your link <a href=\"" + link + "\">" + link + "</a> has been unbanned by admin!</h4>\n"
                + "    <h4>Contact <a href=\"mailto:feedback@oi.io.vn\">feedback@oi.io.vn</a> for more information!</h4>\n"
                + "</div>";
        Email bannedEmail = Email.builder()
                .from("admin@oi.io.vn <admin@oi.io.vn>")
                .to(user.getEmail())
                .subject("Unbanned url notice")
                .html(content)
                .build();
        emailService.sendMail(bannedEmail);
        return true;
    }

}
