package Services.Impl;

import DAL.Impl.UrlDAOImpl;
import DAL.UrlDAO;
import Services.UidService;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author nguyenson
 */
public class UidServiceImpl implements UidService {

    private final UrlDAO urlDAO;

    public UidServiceImpl() {
        this.urlDAO = new UrlDAOImpl();
    }

    @Override
    public String generateUid() {
        return RandomStringUtils.randomAlphanumeric(4);
    }

}
