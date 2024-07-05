package com.example.task3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private String userId;
    private String FirstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
}
