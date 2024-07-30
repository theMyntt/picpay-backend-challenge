package com.themyntt.challenges.picpay.application.utils;

public class CPFValidator {
    public static String validate(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || !cpf.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid CPF");
        }

        StringBuilder repeated = new StringBuilder(cpf.charAt(0) + "" + cpf.charAt(0));
        repeated.append(String.valueOf(cpf.charAt(0)).repeat(cpf.length() - 1));
        if (cpf.contentEquals(repeated)) {
            throw new IllegalArgumentException("Invalid CPF");
        }

        int[] cpfArray = cpf.chars().map(c -> c - '0').toArray();
        int d1 = cpfArray[9];
        int d2 = cpfArray[10];

        int sum1 = 0;
        int sum2 = 0;

        for (int i = 0; i < 9; i++) {
            sum1 += cpfArray[i] * (10 - i);
            sum2 += cpfArray[i] * (11 - i);
        }

        sum2 += d1 * 2;

        int mod1 = (sum1 % 11);
        int mod2 = (sum2 % 11);

        int checkD1 = (mod1 < 2) ? 0 : 11 - mod1;
        int checkD2 = (mod2 < 2) ? 0 : 11 - mod2;

        if (d1 == checkD1 && d2 == checkD2) {
            return cpf;
        } else {
            throw new IllegalArgumentException("Invalid CPF");
        }
    }
}
