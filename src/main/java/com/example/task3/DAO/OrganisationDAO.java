package com.example.task3.DAO;

import com.example.task3.dto.OrganisationDTO;
import com.example.task3.entity.Organisations;
import com.example.task3.entity.Users;

import java.util.List;

public interface OrganisationDAO {
    void save(Organisations organisations);
    Organisations findOrganisation(String orgId);
    void addUsertoOrganisation(Users user, String orgId);
}
