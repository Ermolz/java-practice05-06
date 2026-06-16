package com.academy.practice56.tests;

import com.academy.practice56.AccountSecurityPolicy;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class AccountSecurityPolicyTest {
    @Test
    @Tag("smoke")
    void strongPasswordRequiresSeveralCharacterGroups() {
        assertAll(
                () -> assertTrue(AccountSecurityPolicy.isStrongPassword("SafePass9!")),
                () -> assertFalse(AccountSecurityPolicy.isStrongPassword("safepass9!")),
                () -> assertFalse(AccountSecurityPolicy.isStrongPassword("SafePasswd!"))
        );
    }

    @ParameterizedTest
    @MethodSource("validUsernames")
    @Tag("smoke")
    void acceptsValidUsernames(String username) {
        assertTrue(AccountSecurityPolicy.isValidUsername(username));
    }

    @ParameterizedTest
    @MethodSource("lockRules")
    @Tag("regression")
    void decidesWhenAccountMustBeLocked(int failedAttempts, boolean suspiciousIp, boolean expectedLock) {
        assertEquals(expectedLock, AccountSecurityPolicy.shouldLockAccount(failedAttempts, suspiciousIp));
    }

    @TestFactory
    @Tag("regression")
    Stream<DynamicTest> createsNormalizedRecoveryFileNames() {
        List<Arguments> cases = List.of(
                Arguments.of(" Alice Smith ", "alice-smith-recovery.txt"),
                Arguments.of("BOB_admin", "bob_admin-recovery.txt"),
                Arguments.of("student-42", "student-42-recovery.txt")
        );

        return cases.stream().map(arguments -> {
            Object[] values = arguments.get();
            String username = (String) values[0];
            String expectedFileName = (String) values[1];
            Executable assertion = () -> assertEquals(
                    expectedFileName,
                    AccountSecurityPolicy.normalizeRecoveryFileName(username)
            );

            return DynamicTest.dynamicTest("normalizes '" + username + "'", assertion);
        });
    }

    @Test
    @Tag("regression")
    void recoveryFileNameUsesWindowsSafeCharactersWhenRunningOnWindows() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);

        assumingThat(osName.contains("windows"), () -> {
            String fileName = AccountSecurityPolicy.normalizeRecoveryFileName("Owner: Admin?");

            assertFalse(fileName.contains(":"));
            assertFalse(fileName.contains("?"));
            assertEquals("owner-admin-recovery.txt", fileName);
        });
    }

    static Stream<String> validUsernames() {
        return Stream.of("student", "owner_2026", "admin-user");
    }

    static Stream<Arguments> lockRules() {
        return Stream.of(
                Arguments.of(2, false, false),
                Arguments.of(3, false, false),
                Arguments.of(3, true, true),
                Arguments.of(5, false, true)
        );
    }
}
