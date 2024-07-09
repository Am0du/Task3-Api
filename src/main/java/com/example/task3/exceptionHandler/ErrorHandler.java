package com.example.task3.exceptionHandler;

import com.example.task3.dto.ErrorDTO;
import com.example.task3.dto.SimpleError;
import com.example.task3.exception.LoginError;
import com.example.task3.exception.NoUserError;
import com.example.task3.exception.RegistrationFailedError;
import com.example.task3.exception.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {

    private ErrorDTO error;
    private SimpleError simpleError;

    @Autowired
    public ErrorHandler(ErrorDTO error, SimpleError simpleError){
        this.error = error;
        this.simpleError = simpleError;
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ValidationError exc){
        Map<String, Object> validationE = new HashMap<>();
        validationE.put("errors", exc.getMessages());

        return new ResponseEntity<>(validationE, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception exc){
        simpleError.setStatus("Bad request");
        simpleError.setMessage("Client Error");
        simpleError.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(RegistrationFailedError exc){
        simpleError.setStatus("Bad request");
        simpleError.setMessage(exc.getMessage());
        simpleError.setStatusCode( HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(simpleError,  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(LoginError exc){
        simpleError.setStatus("Bad request");
        simpleError.setMessage("Authentication failed");
        simpleError.setStatusCode(HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(simpleError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(NoUserError exc){
        simpleError.setStatus("Bad request");
        simpleError.setMessage(exc.getMessage());
        simpleError.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(simpleError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        simpleError.setStatus("Not found");
        simpleError.setMessage("Endpoint not found");
        simpleError.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(simpleError, HttpStatus.NOT_FOUND);
    }



}
