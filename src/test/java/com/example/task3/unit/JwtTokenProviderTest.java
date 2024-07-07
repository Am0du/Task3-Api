package com.example.task3.unit;

import com.example.task3.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Test
    public void testTokenGeneration() {
        // Create a mock UserDetails
        UserDetails userDetails = new User("testUser", "password",  Collections.emptyList());

        // Generate a token
        String token = jwtTokenProvider.generateToken(new Authentication() {
            @Override
            public String getName() {
                return userDetails.getUsername();
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return  Collections.emptyList();
            }

            @Override
            public Object getCredentials() {
                return userDetails.getPassword();
            }

            @Override
            public Object getDetails() {
                return userDetails;
            }

            @Override
            public Object getPrincipal() {
                return userDetails;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }
        });

        assertNotNull(token);
    }

    @Test
    public void testTokenExpiry() throws InterruptedException {

        UserDetails userDetails = new User("testUser", "password", Collections.emptyList());


        String token = jwtTokenProvider.generateToken(new Authentication() {
            @Override
            public String getName() {
                return userDetails.getUsername();
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return  Collections.emptyList();
            }

            @Override
            public Object getCredentials() {
                return userDetails.getPassword();
            }

            @Override
            public Object getDetails() {
                return userDetails;
            }

            @Override
            public Object getPrincipal() {
                return userDetails;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

        });

        assertNotNull(token);

        Thread.sleep(jwtTokenProvider.getJwtExpirationDate() + 1000);

        // Validate token expiration
        assertThrows(Exception.class, () -> {
            jwtTokenProvider.getData(token);
        });
    }

//    @Test
//    public void testTokenDataRetrieval() {
//        // Create a mock UserDetails
//        UserDetails userDetails = new User("testUser", "password",  Collections.emptyList());
//
//        // Generate a token
//        String token = jwtTokenProvider.generateToken(new Authentication() {
//            @Override
//            public String getName() {
//                return userDetails.getUsername();
//            }
//
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return  Collections.emptyList();
//            }
//
//            @Override
//            public Object getCredentials() {
//                return userDetails.getPassword();
//            }
//
//            @Override
//            public Object getDetails() {
//                return userDetails;
//            }
//
//            @Override
//            public Object getPrincipal() {
//                return userDetails;
//            }
//
//            @Override
//            public boolean isAuthenticated() {
//                return true;
//            }
//
//            @Override
//            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//
//            }
//
//        });
//
//        assertNotNull(token);
//
//        String username = jwtTokenProvider.getData(token);
//
//        assertEquals("testUser", username);
//    }
//
//    @Test
//    public void testTokenValidation() {
//
//        UserDetails userDetails = new User("testUser", "password",  Collections.emptyList());
//
//
//        String token = jwtTokenProvider.generateToken(new Authentication() {
//            @Override
//            public String getName() {
//                return userDetails.getUsername();
//            }
//
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return  Collections.emptyList();
//            }
//
//            @Override
//            public Object getCredentials() {
//                return userDetails.getPassword();
//            }
//
//            @Override
//            public Object getDetails() {
//                return userDetails;
//            }
//
//            @Override
//            public Object getPrincipal() {
//                return userDetails;
//            }
//
//            @Override
//            public boolean isAuthenticated() {
//                return true;
//            }
//
//            @Override
//            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//            }
//        });
//
//        assertNotNull(token);
//
//        // Validate token
//        assertDoesNotThrow(() -> {
//            jwtTokenProvider.validateToken(token);
//        });
//    }
//
//    @Test
//    public void testInvalidTokenValidation() {
//
//        String invalidToken = "invalidToken";
//
//        // Validate invalid token
//        assertThrows(Exception.class, () -> {
//            jwtTokenProvider.validateToken(invalidToken);
//        });
//    }
//


}

