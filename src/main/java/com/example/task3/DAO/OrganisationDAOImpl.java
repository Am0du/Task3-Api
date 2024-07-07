package com.example.task3.DAO;

import com.example.task3.dto.OrganisationDTO;
import com.example.task3.entity.Organisations;
import com.example.task3.entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class OrganisationDAOImpl implements OrganisationDAO{

    private EntityManager entityManager;
    private OrganisationDTO organisationDTO;

    @Autowired
    public OrganisationDAOImpl(EntityManager entityManager, OrganisationDTO organisationDTO) {
        this.entityManager = entityManager;
        this.organisationDTO = organisationDTO;
    }

    @Override
    public void save(Organisations organisations) {
        entityManager.persist(organisations);
    }

    @Override
    public Organisations findOrganisation(String orgId) {
        TypedQuery<Organisations> query = entityManager.createQuery("select o from Organisations o " +
                "where o.orgId = :data", Organisations.class);
        query.setParameter("data", orgId);
        return query.getSingleResult();

    }

    @Override
    public void addUsertoOrganisation(Users user, String orgId) {
        TypedQuery<Organisations> query = entityManager.createQuery("select o from Organisations o " +
                "where o.orgId = :data", Organisations.class);
        query.setParameter("data", orgId);
       Organisations org =  query.getSingleResult();

       org.addUser(user);
       entityManager.persist(org);

    }


    private OrganisationDTO convertToDTO(Organisations orgEntity){
        organisationDTO.setOrgId(orgEntity.getOrgId());
        organisationDTO.setName(orgEntity.getName());
        organisationDTO.setDescription(orgEntity.getDescription());
        return organisationDTO;
    }

}
