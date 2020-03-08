package ru.otus.spring.hometask02.util;

public class StudentsTestUtils {
    private static final String NUMERIC_REGEXP = "-?\\d+(\\.\\d+)?";

    public static boolean isNumeric(String str) {
        return str.matches(NUMERIC_REGEXP);
    }
}
