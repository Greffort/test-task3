package com.haulmont.testtask.shared;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public final class Mapper implements Serializable {

    private static String toJson(final Object clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(clazz);
    }

    public static <T> T toJavaObject(final Object object, final Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(Mapper.toJson(object), clazz);
    }
}
