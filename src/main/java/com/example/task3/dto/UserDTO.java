package com.example.task3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class UserDTO {

    private String userId;
    private String FirstName;
    private String lastName;
    private String email;
    private String phone;
}
