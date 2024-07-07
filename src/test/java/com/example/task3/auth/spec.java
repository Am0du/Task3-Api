package com.example.task3.auth;
import com.example.task3.dto.ResponseDTO;
import com.example.task3.dto.UserDTO;
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
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.dik@example.com";
        String password = "password123";
        String phone = "232454678981";

        Response response = given()
                .contentType("application/json")
                .body("{\"firstName\": \"" + firstName + "\"," +
                        " \"lastName\": \"" + lastName + "\", " +
                        "\"email\": \"" + email + "\"," +
                        " \"password\": \"" + password + "\", " +
                        "\"phone\": \"" + phone +"\"}")
                .when()
                .post("/auth/register")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Assert the response body for expected user details and access token
        response.then()
                .body("status", equalTo("success"))
                .body("message", equalTo("Registration successful"))
                .body("data.user.firstName", equalTo(firstName))
                .body("data.user.lastName", equalTo(lastName))
                .body("data.user.email", equalTo(email))
                .body("data.user.phone", equalTo(phone))
                .body("data.accessToken", notNullValue());
    }

    @Test
    public void testLoginUserSuccessfully() {
        String email = "john.d@example.com";
        String password = "password123";

        Response response = given()
                .contentType("application/json")
                .body("{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert the response body for expected user details and access token
        response.then()
                .body("status", equalTo("success"))
                .body("message", equalTo("Login successful"))
                .body("data.user.email", equalTo(email))
                .body("data.accessToken", notNullValue())
                .body("data.user.firstName", notNullValue())
                .body("data.user.lastName", notNullValue())
                .body("data.user.userId", notNullValue())
                .body("data.user.phone", notNullValue());

    }

    @Test
    public void testValidationErrorsForMissingRequiredFields() {
        // Missing required fields intentionally
        Response response = given()
                .contentType("application/json")
                .body("{}")
                .when()
                .post("/auth/register")
                .then()
                .statusCode(422) // Assuming 422 is the status code for validation errors
                .extract()
                .response();

        // Assert the response body for appropriate error messages
        response.then()
                .body("errors", notNullValue())
                .body("errors.size()", greaterThan(0))
                .body("errors.field", hasItems("firstName", "lastName", "email", "password"));
    }

    @Test
    public void testFailureOnDuplicateEmailOrUserID() {
        // Register first user
        String email = "jane.d4554445@example.com";
        String password = "password123";
        given()
                .contentType("application/json")
                .body("{\"firstName\": \"Jane\", \"lastName\": \"Doe\", \"email\": \"" + email + "\", \"password\": \"" + password + "\", \"phone\": \"1234567890\"}")
                .when()
                .post("/auth/register")
                .then()
                .statusCode(201);

        // Attempt to register another user with the same email
        String duplicateEmail = "jane.d4554445@example.com";
        Response response = given()
                .contentType("application/json")
                .body("{\"firstName\": \"Jack\", \"lastName\": \"Smith\", \"email\": \"" + duplicateEmail + "\", \"password\": \"password456\", \"phone\": \"0987654321\"}")
                .when()
                .post("/auth/register")
                .then()
                .statusCode(400)
                .extract()
                .response();

        // Assert the response body for appropriate error messages
        response.then()
                .body("message", equalTo("email already exist"))
                .body("statusCode", equalTo(400))
                .body("status", equalTo("Bad request"));
    }
}


