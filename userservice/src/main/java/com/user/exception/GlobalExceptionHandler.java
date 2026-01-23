package com.user.exception;


import com.user.dto.ExceptionDTO;
import com.user.dto.GeneralHttpResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ExceptionDTO.class)
	public ResponseEntity<ExceptionDTO> handleGeneralException(ExceptionDTO ex){
		ExceptionDTO exc = new ExceptionDTO();
		exc.setDate(ex.getDate());
		exc.setStatus(ex.getStatus());
		exc.setCustomMessage(ex.getCustomMessage());
		exc.setCustomBody(ex.getCustomBody());
		return new ResponseEntity<>(exc,exc.getStatus());
	}
	 @Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(
	            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	        Map<String, String> validationErrors = new HashMap<>();
	        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

	        validationErrorList.forEach((error) -> {
	            String fieldName = ((FieldError) error).getField();
	            String validationMsg = error.getDefaultMessage();
	            validationErrors.put(fieldName, validationMsg);
	        });
	        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
	    }
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GeneralHttpResponseDTO> buildErrorResponse(Exception ex) {
		GeneralHttpResponseDTO<Object> response = new GeneralHttpResponseDTO<>();
		HttpStatus httpStatus = determineStatus(ex);

		response.setResponseCode(httpStatus.value());
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

}
