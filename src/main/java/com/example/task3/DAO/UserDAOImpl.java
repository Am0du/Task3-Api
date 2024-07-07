package com.example.task3.DAO;

import com.example.task3.dto.UserDTO;
import com.example.task3.entity.Users;
import com.example.task3.exception.NoUserError;
import com.example.task3.exception.RegistrationFailedError;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO{

    private EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);
    @Autowired
    public UserDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;

    }


    @Override
    public void save(Users user) {
            entityManager.persist(user);
    }

    @Override
    public Users findUserbyEmail(String email){
            TypedQuery<Users> query = entityManager.createQuery("select u from Users u " +
                    "where u.email = :data", Users.class);

            query.setParameter("data", email);
            try {

                return query.getSingleResult();
            }catch (Exception exc){
                throw new NoUserError("user does not exist");
            }



    }

    @Override
    public Users findUser(String userId) {
        TypedQuery<Users> query = entityManager.createQuery(
                "SELECT u FROM Users u " +
                        "WHERE u.userId = :userId", Users.class);
        query.setParameter("userId", userId);

        LOGGER.info(userId);
        return query.getSingleResult();
    }

    @Override
    public Users findUserOrg(String id) {
        TypedQuery<Users> query = entityManager.createQuery("select u from Users u " +
                "JOIN FETCH u.organisations" +
                "where u.userId = :data", Users.class);
        query.setParameter("data", id);

        return query.getSingleResult();
    }

    @Override
    public Users findRelatedUser(String ownerId, String givenId) {
        TypedQuery<Users> query = entityManager.createQuery(
                "SELECT u2 FROM Users u1 " +
                        "JOIN u1.organisations o " +
                        "JOIN o.users u2 " +
                        "WHERE u1.userId = :sourceUserId " +
                        "AND u2.userId = :targetUserId", Users.class);

        query.setParameter("sourceUserId", ownerId);
        query.setParameter("targetUserId", givenId);


        List<Users> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            throw new NoUserError("User does not exist in your organisation");
        } else {
            return resultList.getFirst();
        }
    }



}
