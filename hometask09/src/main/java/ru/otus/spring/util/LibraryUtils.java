package ru.otus.spring.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

public class LibraryUtils {

    private static final String NUMERIC_REGEXP = "-?\\d+(\\.\\d+)?";

    public static boolean isNumeric(String str) {
        return str.matches(NUMERIC_REGEXP);
    }

    public static <T> T retrieveEntity(String identifier,
                                       Function<Long, T> idRetriever, Function<String, T> nameRetriever) {
        T t;
        if (isNumeric(identifier)) {
            long id = Long.parseLong(identifier);
            t = idRetriever.apply(id);
        } else {
            t = nameRetriever.apply(identifier);
        }
        return t;
    }

    public static String toString(Object object) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new LibraryException("Error while object serializing", e);
        }
    }
}
