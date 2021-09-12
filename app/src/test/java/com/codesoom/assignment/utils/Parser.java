package com.codesoom.assignment.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 문자열과 개체간의 변환을 수행한다.
 */
public final class Parser {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 개체를 JSON 문자열로 변환하여 리턴한다.
     *
     * @param object 변환할 개체
     * @return JSON 문자열
     */
    public static String toJson(final Object object) throws Exception {
        final OutputStream outputStream = new ByteArrayOutputStream();
        OBJECT_MAPPER.writeValue(outputStream, object);
        return outputStream.toString();
    }

    /**
     * JSON 문자열을 개체로 변환한다.
     * @param <T> 변환할 개체의 클래스
     * @param json 변환할 문자열
     * @param type 변환할 개체의 클래스 타입
     * @return 개체
     */
    public static <T> T toObject(final String json, final Class<T> type) throws Exception {
        return OBJECT_MAPPER.readValue(json, type);
    }
}
