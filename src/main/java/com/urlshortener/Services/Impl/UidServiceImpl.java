package com.urlshortener.Services.Impl;

import com.urlshortener.DAL.UrlDAO;
import com.urlshortener.Services.UidService;
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
        int length = 4;
        int genNumber = 0;
        String uid;
        while (genNumber <= 10) {
            uid = RandomStringUtils.randomAlphanumeric(length);
            if (urlDAO.isUidAvailable(uid))
                return uid;
            ++genNumber;
            if (genNumber > 2) {
                ++length;
            }
        }
        return null;
    }

}
