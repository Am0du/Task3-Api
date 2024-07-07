package com.example.task3.controller;

import com.example.task3.dto.ResponseDTO;
import com.example.task3.dto.UserDTO;
import com.example.task3.entity.Users;
import com.example.task3.service.AuthService;
import com.example.task3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    private UserService userService;
    private ResponseDTO responseDTO;

    private UserDTO userDTO;

    @Autowired
    public AuthController(AuthService authService, UserService userService, ResponseDTO responseDTO, UserDTO userDTO) {
        this.authService = authService;
        this.userService = userService;
        this.responseDTO = responseDTO;
        this.userDTO = userDTO;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user){
        String pass = user.getPassword();
        ResponseDTO data= userService.registerUser(user);
        String token = authService.loginUser(user.getEmail(), pass);
        data.add("accessToken", token);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user){
        String token = authService.loginUser(user.getEmail(), user.getPassword());
        Users data = userService.getUser(user.getEmail());
        responseDTO.setMessage("Login successful");
        responseDTO.setStatus("success");
        responseDTO.add("accessToken", token);
        responseDTO.add("user", convertUserEntityToUserDto(data));
        return ResponseEntity.ok(responseDTO);


    }

    private UserDTO convertUserEntityToUserDto(Users userEntity) {
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPhone(userEntity.getPhone());
        return userDTO;
    }

}
