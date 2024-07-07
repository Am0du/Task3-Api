package com.example.task3.controller;

import com.example.task3.DAO.OrganisationDAO;
import com.example.task3.dto.*;
import com.example.task3.entity.Organisations;
import com.example.task3.entity.Users;
import com.example.task3.exception.ValidationError;
import com.example.task3.security.JwtTokenProvider;
import com.example.task3.service.OrganisationService;
import com.example.task3.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrganisationController {
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;
    private OrganisationService organisationService;
    private ResponseDTO responseDTO;
    private OrganisationDTO organisationDTO;
    private SimpleResponseDTO simpleResponseDTO;
    public OrganisationController(UserService userService, JwtTokenProvider jwtTokenProvider,
                                  OrganisationService organisationService, ResponseDTO responseDTO,
                                  OrganisationDTO organisationDTO, SimpleResponseDTO simpleResponseDTO) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.organisationService = organisationService;
        this.responseDTO = responseDTO;
        this.organisationDTO = organisationDTO;
        this.simpleResponseDTO = simpleResponseDTO;
    }

    @GetMapping("/organisations")
    public ResponseEntity<?> getAllOrganisation(@RequestHeader("Authorization") String headerValue){
        Users user = userService.getUser(jwtTokenProvider.getData(headerValue.substring(7)));
        List<Organisations> orgs = user.getOrganisations();
        List<OrganisationDTO> orgsList = new ArrayList<>();
        ResponseDTO responseDTO = new ResponseDTO();
        for (Organisations org : orgs) {
            OrganisationDTO organisationDTO = new OrganisationDTO();

            organisationDTO.setDescription(org.getDescription());
            organisationDTO.setOrgId(org.getOrgId());
            organisationDTO.setName(org.getName());
            orgsList.add(organisationDTO);
        }

        responseDTO.add("organisations", orgsList);
        responseDTO.setMessage("User organisation found");
        responseDTO.setStatus("success");

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/organisations/{orgId}")
    public ResponseEntity<?> getOrganisation(@PathVariable("orgId") String orgId){
        Organisations org = organisationService.getOrganisationRecord(orgId);
         ResponseDTO responseDTO = new ResponseDTO(); // Create a new instance locally

            responseDTO.setStatus("success");
            responseDTO.add("orgId", org.getOrgId());
            responseDTO.add("name", org.getName());
            responseDTO.add("description", org.getDescription());
            responseDTO.setMessage("Organisation found");

         return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/organisations")
    public ResponseEntity<?> createOrg(@RequestHeader("Authorization") String headerValue,
                                       @RequestBody Organisations org){
        if(org.getName() == null){
            List<ErrorDetailDTO> error = new ArrayList<>();
            error.add(new ErrorDetailDTO("name", "This field cannot be null"));
            throw new ValidationError(error);
        }

        Users user = userService.getUser(jwtTokenProvider.getData(headerValue.substring(7)));
        org.setId(0);
        org.setOrgId(UUID.randomUUID().toString());

        user.addOrganisation(org);
        ResponseDTO responseDTO = new ResponseDTO(); 
        userService.update(user);

        responseDTO.setMessage("success");
        responseDTO.setStatus("Organisation created Successfully");
        responseDTO.add("orgId", org.getOrgId());
        responseDTO.add("name", org.getName());
        responseDTO.add("description", org.getDescription());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/organisations/{orgId}/users")
    public ResponseEntity<?> addOrg(@RequestBody UserDTO user, @PathVariable String orgId){
        Users users = userService.getUserbyId(user.getUserId());
        Organisations org = organisationService.getOrganisationRecord(orgId);
        org.addUser(users);
        organisationService.save(org);
        simpleResponseDTO.setMessage("User added to organisation successfully");
        simpleResponseDTO.setStatus("success");
        return ResponseEntity.ok(simpleResponseDTO);
    }

}
