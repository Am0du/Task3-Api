package com.example.task3.controller;

import com.example.task3.dto.LoginDTO;
import com.example.task3.dto.ResponseDTO;
import com.example.task3.dto.UserDTO;
import com.example.task3.entity.Users;
import com.example.task3.service.AuthService;
import com.example.task3.service.UserService;
import com.example.task3.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private AuthService authService;
    private UserService userService;
    private ResponseDTO responseDTO;

    private UserDTO userDTO;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService, UserService userService, ResponseDTO responseDTO, UserDTO userDTO) {
        this.authService = authService;
        this.userService = userService;
        this.responseDTO = responseDTO;
        this.userDTO = userDTO;
    }

    @GetMapping("/")
    public String home(){
        return "running";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user){
        String pass = user.getPassword();
        ResponseDTO data= userService.registerUser(user);
        LoginDTO login = new LoginDTO(user.getEmail(), pass);
        String token = authService.loginUser(login);
        data.add("accessToken", token);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login){
        String token = authService.loginUser(login);
        Users data = userService.getUser(login.getEmail());
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
