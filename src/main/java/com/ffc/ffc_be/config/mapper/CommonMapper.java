package com.ffc.ffc_be.config.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonMapper {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    public <T> T map(Object source, Class<T> destination) {
        return modelMapper.map(source, destination);
    }

    public void map(Object source, Object destination) {
        modelMapper.map(source, destination);
    }

    public <T> T readValue(String jsonString, Class<T> destination) throws JsonProcessingException {
        try {
            return objectMapper.readValue(jsonString, destination);
        } catch (JsonProcessingException e) {
            throw e;
        }
    }

    public <T> String writeValueAsString(T object) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw e;
        }
    }

    public <T> T convertValue(Object fromValue, Class<T> toValueType) throws IllegalArgumentException {
        try {
            return objectMapper.convertValue(fromValue, toValueType);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
