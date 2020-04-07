package ru.otus.spring.util;

import java.util.function.Function;

public class LibraryUtils {

    private static final String NUMERIC_REGEXP = "-?\\d+(\\.\\d+)?";

    public static boolean isNumeric(String str) {
        return str.matches(NUMERIC_REGEXP);
    }

    public static <T> T processEntity(String identifier,
                                      Function<Long, T> idProcessor, Function<String, T> nameProcessor) {
        if (isNumeric(identifier)) {
            long id = Long.parseLong(identifier);
            return idProcessor.apply(id);
        } else {
            return nameProcessor.apply(identifier);
        }
    }
}
