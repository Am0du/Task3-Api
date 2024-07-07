package com.example.task3.service;

import com.example.task3.dto.OrganisationDTO;
import com.example.task3.dto.ResponseDTO;
import com.example.task3.dto.SimpleResponseDTO;
import com.example.task3.entity.Organisations;

public interface OrganisationService {

    Organisations getOrganisationRecord(String orgId);

    ResponseDTO createOrganisation(OrganisationDTO org);

    void save(Organisations org);
}
