package com.onlinelearning.test;

import com.urlshortener.DAL.DBContext;
import com.urlshortener.DAL.Impl.DBContextImpl;
import com.urlshortener.Utils.DotEnv;
import java.sql.Connection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class ConfigurationTest {
    
    @Order(1)
    @Test
    public void testDotEnv() {
        String envTestMessage = DotEnv.get("ENV_TEST");
        Assertions.assertEquals("Hello World!", envTestMessage);
    }
    
    @Order(2)
    @Test
    public void testDBContext() {
        DBContext dbContext = new DBContextImpl();
        Connection connection = dbContext.getConnection();
        Assertions.assertNotNull(connection);
    }
}
