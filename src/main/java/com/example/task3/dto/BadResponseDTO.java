package com.example.task3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class BadResponseDTO {

   private String status;
   private String message;
   private int statusCode;
}
