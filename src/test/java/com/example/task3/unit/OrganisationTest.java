package com.example.task3.service;

import com.example.task3.DAO.UserDAO;
import com.example.task3.entity.Organisations;
import com.example.task3.entity.Users;
import com.example.task3.exception.NoUserError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrganisationTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldNotAllowAccessToOtherOrganizationsData() {
        // Arrange: Create mock users and organizations
        Users user1 = new Users("user1", "FirstName1", "LastName1", "password", "email1@example.com", "1234567890");
        Users user2 = new Users("user2", "FirstName2", "LastName2", "password", "email2@example.com", "0987654321");

        Organisations org1 = new Organisations("org1", "Organization 1", "Description 1");
        Organisations org2 = new Organisations("org2", "Organization 2", "Description 2");

        user1.setOrganisations(Arrays.asList(org1));
        user2.setOrganisations(Arrays.asList(org2));

        when(userDAO.findUser("user1")).thenReturn(user1);
        when(userDAO.findUser("user2")).thenReturn(user2);
        when(userDAO.findRelatedUser("user1", "org2")).thenReturn(null);

        // Act & Assert: Verify access control
        Users foundUser = userService.findUserInOrg("user1", "org2");

        assertThrows(NoUserError.class, () -> {
            if (foundUser == null) {
                throw new NoUserError("User does not exist in the organization");
            }
        });

        verify(userDAO, times(1)).findRelatedUser("user1", "org2");
    }
}
