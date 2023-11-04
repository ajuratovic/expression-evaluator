package org.ajuratovic.expression_evaluator.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage<String>> handleException(Exception exception) {
        ErrorMessage<String> errorMessage = new ErrorMessage<>(exception.getMessage());
        ResponseEntity<ErrorMessage<String>> responseEntity = new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

        return responseEntity;
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, JpaObjectRetrievalFailureException.class, EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage<String>> handleSimpleBadRequests(Exception exception) {
        ErrorMessage<String> errorMessage = new ErrorMessage<>(exception.getMessage());
        ResponseEntity<ErrorMessage<String>> responseEntity = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);

        return responseEntity;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage<List<ObjectError>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        ErrorMessage<List<ObjectError>> errorMessage = new ErrorMessage<>(errors);
        ResponseEntity<ErrorMessage<List<ObjectError>>> responseEntity = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);

        return responseEntity;
    }
}
