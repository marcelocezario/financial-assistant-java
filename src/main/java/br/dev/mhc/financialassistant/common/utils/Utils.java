package br.dev.mhc.financialassistant.common.utils;

import java.util.Random;

import static java.util.Objects.isNull;

public class Utils {

    private static final Random random = new Random();

    public static boolean isIntegerNumber(String text) {
        if (isNull(text)) {
            return false;
        }
        return text.matches("-?\\d+");
    }

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
