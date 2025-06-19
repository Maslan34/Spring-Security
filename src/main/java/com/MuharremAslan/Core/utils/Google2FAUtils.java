package com.MuharremAslan.Core.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;

public class Google2FAUtils {

    private static final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    public static boolean verifyCode(String secretKey, String code) {
        int codeInt;

        try {
            codeInt = Integer.parseInt(code);
        } catch (NumberFormatException e) {
            return false; // Invalid if the code is not numeric
        }

        return gAuth.authorize(secretKey, codeInt);
    }
}
