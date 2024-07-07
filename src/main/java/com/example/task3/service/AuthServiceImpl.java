package com.example.task3.service;

import com.example.task3.dto.ErrorDetailDTO;
import com.example.task3.dto.LoginDTO;
import com.example.task3.exception.LoginError;
import com.example.task3.exception.ValidationError;
import com.example.task3.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService{
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public String loginUser(LoginDTO login) {
        List<ErrorDetailDTO> error = validateUser(login);

        if (error.isEmpty()){
            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        login.getEmail(), login.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String token = jwtTokenProvider.generateToken(authentication);

                return token;
            }catch (Exception exc){
                throw new LoginError();
            }
        }else{
            throw new ValidationError(error);
        }
    }

    private List<ErrorDetailDTO> validateUser(LoginDTO user) {
        List<ErrorDetailDTO> errors = new ArrayList<>();

        if (user.getEmail() == null) {
            errors.add(new ErrorDetailDTO("email", "This field cannot be null"));
        }
        if (user.getPassword() == null) {
            errors.add(new ErrorDetailDTO("password", "This field cannot be null"));
        }
        return errors;
    }
}
