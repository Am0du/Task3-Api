package com.example.task3.controller;

import com.example.task3.dto.ResponseDTO;
import com.example.task3.entity.Organisations;
import com.example.task3.entity.Users;
import com.example.task3.security.JwtTokenProvider;
import com.example.task3.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private ResponseDTO responseDTO;
    private JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService,JwtTokenProvider jwtTokenProvider, ResponseDTO responseDTO) {
        this.userService = userService;
        this.jwtTokenProvider =  jwtTokenProvider;
        this.responseDTO = responseDTO;
    }

    private void addUserDetailsToResponse(ResponseDTO responseDTO, Users user) {
        responseDTO.add("firstName", user.getFirstName());
        responseDTO.add("lastName", user.getLastName());
        responseDTO.add("userId", user.getUserId());
        responseDTO.add("email", user.getEmail());
        responseDTO.add("phone", user.getPhone());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?>  userRecord(@PathVariable("id") String userId,
                                         @RequestHeader("Authorization") String headerValue){
        Users user = userService.getUser(jwtTokenProvider.getData(headerValue.substring(7)));

        ResponseDTO responseDTO = new ResponseDTO();
        if (user.getUserId().equals(userId)) {
            responseDTO.setStatus("success");
            responseDTO.setMessage("User found");
            addUserDetailsToResponse(responseDTO, user);
            return ResponseEntity.ok(responseDTO);
        }

        Users otherUser = userService.findUserInOrg(user.getUserId(), userId);

        responseDTO.setMessage("User found");
        responseDTO.setStatus("success");
        addUserDetailsToResponse(responseDTO, otherUser);
        return ResponseEntity.ok(responseDTO);

    }
}
