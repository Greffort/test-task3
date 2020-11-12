package com.haulmont.testtask.shared;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

/*
 * Class Converts objects of different types
 * @version 12.11.2020
 * Created by Greffort
 */

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
