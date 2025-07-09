package com.wesleyadiel.valeapi.util;

import java.util.UUID;

public class IdUtil {

    private IdUtil() {
        // impede instanciação
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String generateCompactId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
