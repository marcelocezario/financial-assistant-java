package br.dev.mhc.financialassistant.common.utils;

import static java.util.Objects.isNull;

public class Utils {

    public static boolean isIntegerNumber(String text) {
        if (isNull(text)) {
            return false;
        }
        return text.matches("-?\\d+");
    }

}
