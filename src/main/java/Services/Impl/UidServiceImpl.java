package Services.Impl;

import DAL.Impl.UrlDAOImpl;
import DAL.UrlDAO;
import Services.UidService;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author nguyenson
 */
@Default
public class UidServiceImpl implements UidService {

    @Inject
    private UrlDAO urlDAO;

    public UidServiceImpl() {
    }

    @Override
    public String generateUid() {
        return RandomStringUtils.randomAlphanumeric(4);
    }

}
