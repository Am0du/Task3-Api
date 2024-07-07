package com.example.task3.service;

import com.example.task3.dto.ResponseDTO;
import com.example.task3.dto.UserDTO;
import com.example.task3.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    ResponseDTO registerUser(Users userDTO);

    Users getUserbyId(String user_id);

    Users getUser(String email);

    void update(Users user);

    Users findUserInOrg(String OwnID, String OtherId);
}
