package com.example.task3.DAO;

import com.example.task3.dto.OrganisationDTO;
import com.example.task3.dto.UserDTO;
import com.example.task3.entity.Organisations;
import com.example.task3.entity.Users;

public interface UserDAO {

    void save(Users user);

    Users findUserbyEmail(String email);

    Users findUser(String user_id);

    Users findUserOrg(String id);

    Users findRelatedUser(String ownerId, String givenId);


}
