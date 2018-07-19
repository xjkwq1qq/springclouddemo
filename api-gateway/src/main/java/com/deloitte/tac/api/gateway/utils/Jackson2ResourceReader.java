package com.deloitte.tac.api.gateway.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

/**
 * @Description 资源读取
 * @Author qiangwang
 * @Createion Date 23/11/2017
 */
public class Jackson2ResourceReader {
    private final static ObjectMapper mapper = new ObjectMapper();

    /**
     * 读取list
     * @param resource
     * @param clas
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> readListFromResource(Resource resource, Class<T> clas) throws IOException {
        return mapper.readValue(resource.getInputStream(), getCollectionType(List.class, clas));
    }

    /**
     * 读取list
     * @param data
     * @param clas
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> readListFromResource(String data, Class<T> clas) throws IOException {
        return mapper.readValue(data, getCollectionType(List.class, clas));
    }

    /**
     * 读取class
     * @param resource
     * @param clas
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> T readFromResource(Resource resource, Class<T> clas) throws IOException {
        return mapper.readValue(resource.getInputStream(), clas);
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
