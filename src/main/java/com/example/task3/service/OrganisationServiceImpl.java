package com.example.task3.service;

import com.example.task3.DAO.OrganisationDAO;
import com.example.task3.dto.OrganisationDTO;
import com.example.task3.dto.ResponseDTO;
import com.example.task3.dto.SimpleResponseDTO;
import com.example.task3.entity.Organisations;
import org.springframework.stereotype.Service;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    private OrganisationDAO organisationDAO;

    public OrganisationServiceImpl(OrganisationDAO organisationDAO) {
        this.organisationDAO = organisationDAO;
    }


    @Override
    public Organisations getOrganisationRecord(String orgId) {
        return organisationDAO.findOrganisation(orgId);

    }

    @Override
    public ResponseDTO createOrganisation(OrganisationDTO org) {
        return null;
    }

    @Override
    public void save(Organisations org) {
        organisationDAO.save(org);
    }

}
