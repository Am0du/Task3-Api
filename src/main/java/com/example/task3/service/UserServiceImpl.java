package com.example.task3.service;

import com.example.task3.DAO.UserDAO;
import com.example.task3.dto.ErrorDetailDTO;
import com.example.task3.dto.ResponseDTO;
import com.example.task3.dto.UserDTO;
import com.example.task3.entity.Organisations;
import com.example.task3.entity.Users;
import com.example.task3.exception.LoginError;
import com.example.task3.exception.NoUserError;
import com.example.task3.exception.RegistrationFailedError;
import com.example.task3.exception.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private ResponseDTO responseDTO;
    private UserDAO userDAO;

    private UserDTO userDTO;
    private PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    public UserServiceImpl(ResponseDTO responseDTO, UserDAO userDAO,
                           PasswordEncoder passwordEncoder, UserDTO userDTO) {
        this.responseDTO = responseDTO;
        this.userDAO = userDAO;
         this.userDTO = userDTO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseDTO registerUser(Users user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setId(0);
        List<ErrorDetailDTO> error = validateUser(user);

        if (error.isEmpty()){
            user.setPassword(encryptPassword(user.getPassword()));
            Organisations org = new Organisations();
            org.setId(0);
            org.setOrgId(UUID.randomUUID().toString());
            org.setName(user.getFirstName()+"'s Organisation");
            user.addOrganisation(org);
            try {
                userDAO.save(user);
            }catch (Exception exc) {
                throw new RegistrationFailedError("Registration unsuccessful");
            }
        }else{
            throw new ValidationError(error);
        }

        responseDTO.setStatus("success");
        responseDTO.setMessage("Registration successful");
        responseDTO.add("user", convertUserEntityToUserDto(user));
        return responseDTO;


    }

    @Override
    public Users getUserbyId(String user_id) {
        try {
            LOGGER.info(user_id);
            return userDAO.findUser(user_id);
        }catch (Exception c){
            throw new NoUserError("User does not exist");
        }

    }

    @Override
    public Users getUser(String email) {
        return userDAO.findUserbyEmail(email);
    }

    @Override
    public void update(Users user) {
        try {
            userDAO.save(user);
        }catch (Exception e){
            throw new NoUserError("Client error");
        }
    }

    @Override
    public Users findUserInOrg(String ownId, String otherId) {
        return userDAO.findRelatedUser(ownId, otherId);
    }


    private List<ErrorDetailDTO> validateUser(Users user) {
        List<ErrorDetailDTO> errors = new ArrayList<>();

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            errors.add(new ErrorDetailDTO("firstName", "This field cannot be empty"));
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            errors.add(new ErrorDetailDTO("lastName", "This field cannot be null"));
        }
        if(!user.getEmail().contains("@") || !user.getEmail().contains(".") ){
            errors.add(new ErrorDetailDTO("email", "Invalid email address"));
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.add(new ErrorDetailDTO("email", "This field cannot be null"));
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errors.add(new ErrorDetailDTO("password", "This field cannot be null"));
        }


        return errors;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws LoginError {

        Users user = userDAO.findUserbyEmail(email);

        if (user == null) {
            throw new LoginError();
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles()
                .build();
    }




    private String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }

    private UserDTO convertUserEntityToUserDto(Users userEntity) {
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPhone(userEntity.getPhone());
        return userDTO;
    }

//    private Users convertUserDTOToUserEntity(UserDTO users) {
//        Users user = new Users();
//        user.setUserId(users.getUserId());
//        user.setFirstName(users.getFirstName());
//        user.setLastName(users.getLastName());
//        user.setEmail(users.getEmail());
//        user.setPhone(users.getPhone());
//        return user;
//    }
}
