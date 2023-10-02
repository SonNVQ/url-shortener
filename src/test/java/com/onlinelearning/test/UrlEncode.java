package com.onlinelearning.test;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author nguyenson
 */
public class UrlEncode {

    public static void main(String[] args) {
        String url = "http://example.com/path?param=value&param2=value2";

        try {
            String encodedUrl = encodeUrl(url);
            System.out.println("Encoded URL: " + encodedUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encodeUrl(String url) throws Exception {
        String encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                .replaceAll("%3A", ":")
                .replaceAll("%2F", "\\\\")
                .replaceAll("\\+", "%20")
                .replaceAll("%21", "!")
                .replaceAll("%27", "'")
                .replaceAll("%28", "(")
                .replaceAll("%29", ")")
                .replaceAll("%7E", "~");

        return encodedUrl;
    }

}
