package com.urlshortener.Utils;

import io.github.cdimascio.dotenv.Dotenv;

public class DotEnv {
    private static final Dotenv dotenv = Dotenv.configure().directory(".").load();

    private DotEnv() {
    }
    
    public static String get(String key) {
        return dotenv.get(key);
    }
    
    public static String get(String key, String defaultValue) {
        return dotenv.get(key, defaultValue);
    }
}