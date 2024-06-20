package br.dev.mhc.financialassistant.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Random;

public class TestUtils {

    private static final Random random = new Random();

    public static Integer generateRandomInteger(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("The min value cannot be greater than the max value");
        }
        return random.nextInt((max - min) + 1) + min;
    }

    public static BigDecimal generateRandomBigDecimal(int min, int max, int scale) {
        if (min >= max || scale < 0) {
            throw new IllegalArgumentException("The min value cannot be greater than the max value");
        }
        int integerPart = random.nextInt(max - min) + min;
        int decimalPart = random.nextInt(100);

        BigDecimal result = BigDecimal.valueOf(integerPart + (decimalPart / 100.0));
        return result.setScale(scale, RoundingMode.HALF_UP);
    }

    public static LocalDate generateRandomLocalDate(int startYear, int endYear) {
        if (startYear > endYear) {
            throw new IllegalArgumentException("Start year must be greater than end year");
        }
        int year = random.nextInt(endYear - startYear + 1) + startYear;
        int month = random.nextInt(12) + 1;
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        int day = random.nextInt(daysInMonth) + 1;

        return LocalDate.of(year, month, day);
    }

    public static LocalDateTime generateRandomLocalDateTime(int startYear, int endYear) {
        if (startYear > endYear) {
            throw new IllegalArgumentException("Start year must be greater than end year");
        }
        int year = random.nextInt(endYear - startYear + 1) + startYear;
        int month = random.nextInt(12) + 1;
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        int day = random.nextInt(daysInMonth) + 1;
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        int nano = random.nextInt(999999999);

        return LocalDateTime.of(year, month, day, hour, minute, second, nano);
    }

    public static String generateLoremIpsum(int wordCount) {
        String loremIpsumBase = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        String[] words = loremIpsumBase.split(" ");
        StringBuilder loremIpsum = new StringBuilder();

        for (int i = 0; i < wordCount; i++) {
            loremIpsum.append(words[i % words.length]).append(" ");
        }

        return loremIpsum.toString().trim();
    }
}
