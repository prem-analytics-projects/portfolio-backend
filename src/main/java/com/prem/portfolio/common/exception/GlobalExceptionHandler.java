package com.prem.portfolio.common.exception;

import com.prem.portfolio.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                Map.of(),
                request
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DuplicateResourceException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                Map.of(),
                request
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                Map.of(),
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.putIfAbsent(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors,
                request
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                Map.of(),
                request
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid or malformed JSON request",
                Map.of(),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception exception,
            HttpServletRequest request
    ) {
        // Log the full exception on the server, not in the API response.
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected server error occurred",
                Map.of(),
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            Map<String, String> errors,
            HttpServletRequest request
    ) {
        ErrorResponse response = new ErrorResponse(
                false,
                message,
                errors,
                request.getRequestURI(),
                OffsetDateTime.now()
        );

        return ResponseEntity.status(status).body(response);
    }
}