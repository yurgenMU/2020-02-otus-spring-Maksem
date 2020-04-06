package ru.otus.spring.util;


import java.util.function.Function;

public class LibraryUtils {

    private static final String NUMERIC_REGEXP = "-?\\d+(\\.\\d+)?";

    public static boolean isNumeric(String str) {
        return str.matches(NUMERIC_REGEXP);
    }

    public static <T> T retrieveEntity(String bookIdentifier,
                                       Function<Long, T> idRetriever, Function<String, T> nameRetriever) {
        T t;
        if (isNumeric(bookIdentifier)) {
            long bookId = Long.parseLong(bookIdentifier);
            t = idRetriever.apply(bookId);
        } else {
            t = nameRetriever.apply(bookIdentifier);
        }
        return t;
    }
}
