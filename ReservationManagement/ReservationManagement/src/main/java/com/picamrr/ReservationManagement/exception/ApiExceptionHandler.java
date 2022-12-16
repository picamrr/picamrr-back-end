package com.picamrr.ReservationManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RestaurantNotFoundException.class, ReservationNotFoundException.class})
    protected ResponseEntity<ApiExceptionDetails> handleNotFoundException(Exception exception){
        return getResponseEntity(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotEnoughSeatsException.class})
    protected ResponseEntity<ApiExceptionDetails> handleBadRequestException(Exception exception){
        return getResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<ApiExceptionDetails> handleForbiddenException(Exception exception){
        return getResponseEntity(exception, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<ApiExceptionDetails> getResponseEntity(Exception exception, HttpStatus httpStatus) {
        ApiExceptionDetails apiExceptionDetails = new ApiExceptionDetails();
        apiExceptionDetails.setHttpStatus(httpStatus);
        apiExceptionDetails.setMessage(exception.getMessage());
        apiExceptionDetails.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(apiExceptionDetails, apiExceptionDetails.getHttpStatus());

    }
}
