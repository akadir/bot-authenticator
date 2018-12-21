package com.kadir.twitterbots.util;

/**
 * @author akadir
 * Date: 08/12/2018
 * Time: 14:24
 */
public class DataUtil {

    private DataUtil() {
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

}
