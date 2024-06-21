package com.montrackjpa.JPASpringBootExercises.exceptions;

import com.montrackjpa.JPASpringBootExercises.responses.Response;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.UnknownHostException;
import java.util.HashMap;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandling {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Response<HashMap<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex){
        log.error(ex.getMessage(), ex);
        HashMap<String, String> errorsMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorsMap.put(error.getField(), error.getDefaultMessage());
        });
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Unable to process the request", errorsMap);
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public final ResponseEntity<Response<HashMap<String, String>>> handleConstraintValidation(MethodArgumentNotValidException ex){
        log.error(ex.getMessage(), ex);
        HashMap<String, String> errorsMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorsMap.put(error.getField(), error.getDefaultMessage());
        });
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Unable to process the request", errorsMap);
    }




    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response<String>> handleAllExceptions(Exception ex) {

        log.error(ex.getMessage(), ex);

        if (ex.getCause() instanceof UnknownHostException) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(),
                    ex.getLocalizedMessage());
        }

        return Response.failedResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "We are unable to process your request at this time, please try again later.", ex.getMessage());
    }

    @ExceptionHandler(InputException.class)
    public ResponseEntity<Response<Object>> handleInputException(InputException ex) {
        return Response.failedResponse(ex.getHttpStatus().value(), ex.getMessage());
    }
}
