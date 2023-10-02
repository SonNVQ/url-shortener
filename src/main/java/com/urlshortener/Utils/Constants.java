package com.urlshortener.Utils;

public class Constants {

    public static final String FULL_DOMAIN = DotEnv.get("FULL_DOMAIN");

    public static final String GOOGLE_CLIENT_ID = DotEnv.get("GOOGLE_CLIENT_ID");

    public static final int PASSWORD_RESET_TOKEN_LIVE_TIME_IN_MINUTE = 10;

    //https://mkyong.com/regular-expressions/how-to-validate-username-with-regular-expression/
    public static final String REGEX_USERNAME_CHECK = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){6,20}[a-zA-Z0-9]$";

    //https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    public static final String REGEX_PASSWORD_CHECK = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,20}$";

    public static final Integer PAGINATION_MAX_PAGE_SIZE = 50;

    public static final Integer PAGINATION_DEFAULT_PAGE_SIZE = 5;

    public static final String MIN_DATE_STRING = "1900-01-01";

    public static final String MAX_DATE_STRING = "9999-12-31";

}
