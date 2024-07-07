

# Task3 Application

Task3 is a Spring Boot application designed to manage user authentication, user details, and organization management through RESTful APIs.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup](#setup)
- [API Endpoints](#api-endpoints)
- [Usage Examples](#usage-examples)
- [Testing](#testing)


## Features

- **Authentication**: Secure user registration and login using JWT (JSON Web Tokens).
- **User Management**: CRUD operations for managing user details and associations.
- **Organisation Management**: APIs for creating, retrieving, and managing organisations.

## Technologies Used

- Java
- Spring Boot
- PostgreSQL (or your preferred database)
- Hibernate/JPA
- JWT for authentication
- Maven (or Gradle) for dependency management

## Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/your/repository.git
   cd task3
   ```

2. **Set up Database**

    - Create a PostgreSQL database (or your preferred database).
    - Update `application.properties` with your database credentials and settings.

3. **Build and Run**

    - Build the project using Maven:

      ```bash
      mvn clean install
      ```

    - Run the application:

      ```bash
      you can run it directly from your IDE by running the `Task3Application` class.
      ```

4. **Accessing APIs**

    - The application will run on `http://localhost:8080` by default.
    - Use tools like Postman or curl to interact with the APIs.

## API Endpoints

- **AuthController**: Handles user registration and authentication.

    - `POST /auth/register`: Register a new user.
    - `POST /auth/login`: Authenticate and login a user.

- **UserController**: Manages user details.

    - `GET /api/users/{id}`: Retrieve user details by user ID.

- **OrganisationController**: Manages organisation details.

    - `GET /api/organisations`: Retrieve all organisations associated with the current user.
    - `GET /api/organisations/{orgId}`: Retrieve details of a specific organisation.
    - `POST /api/organisations`: Create a new organisation.
    - `POST /api/organisations/{orgId}/users`: Add a user to an organisation.

## Usage Examples

### Register a New User

**POST /auth/register**
```http
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "password123"
}
```

### Login User
**POST /auth/login**
```http

Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "password123"
}
```


### Retrieve User Details

**GET /api/organisations**

- **Purpose:** Fetches all organizations associated with the authenticated user.
- **Authorization:** Bearer \<JWT Token\>
- **Response:**
  ```json
  {
    "organisations": [
      {
        "orgId": "organization_id",
        "name": "Organization Name",
        "description": "Organization Description"
      },
      ...
    ],
    "message": "User organization found",
    "status": "success"
  }
  ```

### Create a New Organisation

**POST /api/organisations**

- **Purpose:** Creates a new organization.
- **Authorization:** Bearer \<JWT Token\>
- **Content-Type:** application/json
- **Request Body:**
  ```json
  {
    "name": "Example Org",
    "description": "An example organisation"
  }
  ```
- **Response (Success):** HTTP status 201 Created
  ```json
  {
    "orgId": "organization_id",
    "name": "Example Org",
    "description": "An example organisation",
    "message": "Organisation created Successfully",
    "status": "success"
  }
  ```
- **Validation:** Throws `ValidationError` if `name` field is null.

### Add User to Organisation

**POST /api/organisations/{orgId}/users**

- **Purpose:** Adds a user to an organization.
- **Path Variable:** `orgId` - ID of the organization to add the user to.
- **Request Body:** JSON representation of the user (`UserDTO`).
- **Response (Success):**
  ```json
  {
    "message": "User added to organisation successfully",
    "status": "success"
  }
  ```

## Testing


## Unit Testing

### Purpose

Unit tests verify the functionality of individual components (classes and methods) in isolation to ensure they behave as expected.

### Technologies Used

- **JUnit 5**: Java framework for writing and running tests.
- **Mockito**: Mocking framework to create mock objects for dependencies.

### Running Unit Tests

To run unit tests, execute the following command in your project directory:

```bash
mvn clean test
```

### Example: OrganisationTest

```java
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
        // Test logic here
    }
}
```

## End-to-End (E2E) Testing

### Purpose

E2E tests validate the integration of different system components from start to finish, ensuring the entire system works as expected.

### Technologies Used

- **RestAssured**: Java library for testing RESTful services.
- **JUnit 5**: Used for organizing and running tests.

### Running End-to-End Tests

Make sure your application is running locally on `http://localhost:8080` before executing the tests.

```bash
mvn clean test -Dtest=spec
```

### Example: spec

```java
package com.example.task3.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class spec {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testRegisterUserSuccessfullyWithDefaultOrganization() {
        // Test registration logic here
    }

    @Test
    public void testLoginUserSuccessfully() {
        // Test login logic here
    }

    @Test
    public void testValidationErrorsForMissingRequiredFields() {
        // Test validation errors logic here
    }

    @Test
    public void testFailureOnDuplicateEmailOrUserID() {
        // Test duplicate email or userID logic here
    }
}
```

