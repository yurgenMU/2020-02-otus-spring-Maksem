package ru.otus.spring.util;

import java.io.InputStream;

public class StudentsTestUtils {
    private static final String NUMERIC_REGEXP = "-?\\d+(\\.\\d+)?";

    public static boolean isNumeric(String str) {
        return str.matches(NUMERIC_REGEXP);
    }

    public static InputStream loadResource(String resourcePath) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(resourcePath);
    }
}
