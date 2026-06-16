package com.academy.practice56;

import java.util.Locale;
import java.util.Objects;

public final class AccountSecurityPolicy {
    private AccountSecurityPolicy() {
    }

    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 10) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char character : password.toCharArray()) {
            if (Character.isUpperCase(character)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(character)) {
                hasLowercase = true;
            } else if (Character.isDigit(character)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecial;
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 16) {
            return false;
        }

        for (char character : username.toCharArray()) {
            if (!Character.isLetterOrDigit(character) && character != '_' && character != '-') {
                return false;
            }
        }

        return true;
    }

    public static boolean shouldLockAccount(int failedAttempts, boolean suspiciousIp) {
        return failedAttempts >= 5 || (failedAttempts >= 3 && suspiciousIp);
    }

    public static String normalizeRecoveryFileName(String username) {
        Objects.requireNonNull(username, "username must not be null");

        return username
                .trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9_-]+", "-")
                .replaceAll("^-+|-+$", "")
                + "-recovery.txt";
    }
}
