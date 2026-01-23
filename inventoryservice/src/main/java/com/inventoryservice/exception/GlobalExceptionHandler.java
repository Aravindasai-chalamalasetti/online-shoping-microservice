package com.inventoryservice.exception;

import com.inventoryservice.dto.GeneralHttpResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle @Valid validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralHttpResponseDTO<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        GeneralHttpResponseDTO<Map<String, String>> response = new GeneralHttpResponseDTO<>();
        response.setResponseCode(403);
        response.setResponseMessage("Validation Failed");
        response.setResponseBody(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GeneralHttpResponseDTO> handleNotFoundExceptions(ResourceNotFoundException ex){
        GeneralHttpResponseDTO responseDTO = new GeneralHttpResponseDTO<>();
        responseDTO.setResponseCode(404);
        responseDTO.setResponseMessage(ex.getMessage());
        responseDTO.setDate(new Date());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralHttpResponseDTO<List<String>> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return buildErrorResponse(400, "Validation failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralHttpResponseDTO> buildErrorResponse(Exception ex) {
        GeneralHttpResponseDTO<Object> response = new GeneralHttpResponseDTO<>();
        HttpStatus httpStatus = determineStatus(ex);

        response.setResponseCode(httpStatus.value()); // Use HTTP status code
        response.setResponseMessage(ex.getMessage());
        response.setResponseBody(null);
        return new ResponseEntity<>(response, httpStatus);
    }

    private HttpStatus determineStatus(Exception ex) {

        if (ex instanceof IllegalArgumentException ||
                ex instanceof ConstraintViolationException ||
                ex instanceof MethodArgumentNotValidException) {
            return HttpStatus.BAD_REQUEST; // 400
        }

        if (ex instanceof AccessDeniedException) {
            return HttpStatus.FORBIDDEN; // 403
        }

        if (ex instanceof NoHandlerFoundException) {
            return HttpStatus.NOT_FOUND; // 404
        }

        if (ex instanceof HttpMessageNotReadableException) {
            return HttpStatus.BAD_REQUEST; // 400
        }

        if (ex instanceof DataIntegrityViolationException) {
            return HttpStatus.CONFLICT; // 409
        }

        // Default for unhandled exceptions
        return HttpStatus.INTERNAL_SERVER_ERROR; // 500
    }

    private <T> GeneralHttpResponseDTO<T> buildErrorResponse(int code, String message, T body) {
        GeneralHttpResponseDTO<T> response = new GeneralHttpResponseDTO<>();
        response.setResponseCode(code);
        response.setResponseMessage(message);
        response.setResponseBody(body);
        return response;
    }
}
