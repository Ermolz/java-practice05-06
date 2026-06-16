package com.academy.practice56;

public class Main {
    public static void main(String[] args) {
        String recoveryFileName = AccountSecurityPolicy.normalizeRecoveryFileName("Demo User");

        System.out.println("Recovery file: " + recoveryFileName);
    }
}
