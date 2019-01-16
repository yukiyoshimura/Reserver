package com.reserver.web.util;

import java.security.SecureRandom;
import java.util.Base64;

public final class CommonUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String getToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        return token;

    }
}
