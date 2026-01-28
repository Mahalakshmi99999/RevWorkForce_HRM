package org.example.Util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private static final int SALT_ROUNDS = 12;

    public static String hashPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(SALT_ROUNDS));
    }

    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
