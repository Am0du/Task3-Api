package com.example.task3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ResponseDTO {
    private String status;
    private String message;
    private Map<String, Object> data;

    public void add(String key, Object value){
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }
}
