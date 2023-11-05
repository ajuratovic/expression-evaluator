package org.ajuratovic.expression_evaluator.exception;

import lombok.AllArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class GlobalExceptionHandler {

    public static final String ISSUE_OCCURRED_LOG_MESSAGE = "Issue occurred! Returning response: {}";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage<String>> handleException(Exception exception) {
        ErrorMessage<String> errorMessage = new ErrorMessage<>(exception.getMessage());
        ResponseEntity<ErrorMessage<String>> responseEntity = new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

        return responseEntity;
    }

    @ExceptionHandler({JpaObjectRetrievalFailureException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorMessage<String>> handleInvalidExpressionId(Exception exception) {
        ErrorMessage<String> errorMessage = new ErrorMessage<>(exception.getMessage());
        ResponseEntity<ErrorMessage<String>> responseEntity = new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);

        return responseEntity;
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMessage<String>> handleInvalidHttpMethod(Exception exception) {
        ErrorMessage<String> errorMessage = new ErrorMessage<>(exception.getMessage());
        ResponseEntity<ErrorMessage<String>> responseEntity = new ResponseEntity<>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);

        return responseEntity;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, ParseException.class, EvaluationException.class})
    public ResponseEntity<ErrorMessage<List<ObjectError>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        ErrorMessage<List<ObjectError>> errorMessage = new ErrorMessage<>(errors);
        ResponseEntity<ErrorMessage<List<ObjectError>>> responseEntity = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);

        return responseEntity;
    }
}
