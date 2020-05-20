package br.com.basis.madre.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.elasticsearch.core.EntityMapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.data.mapping.MappingException;

public class CustomEntityMapper implements EntityMapper {

    private ObjectMapper objectMapper;

    public CustomEntityMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
    }

    @Override
    public String mapToString(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    @Override
    public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
        return objectMapper.readValue(source, clazz);
    }

    @Override
    public <T> T readObject (Map<String, Object> source, Class<T> targetType) {

        try {
            return mapToObject(mapToString(source), targetType);
        } catch (IOException e) {
            throw new MappingException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> mapObject(Object source) {

        try {
            return objectMapper.readValue(mapToString(source), HashMap.class);
        } catch (IOException e) {
            throw new MappingException(e.getMessage(), e);
        }
    }

}
