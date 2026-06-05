package com.fitcoach.client.global.exception;

import com.fitcoach.client.global.response.ApiResponse;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
    ErrorCode errorCode = e.getErrorCode();
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(ApiResponse.error(errorCode.getStatus().value(), errorCode.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidationException(
      MethodArgumentNotValidException e) {
    String message = e.getBindingResult().getFieldErrors().stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.joining(", "));
    return ResponseEntity
        .badRequest()
        .body(ApiResponse.error(400, message));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
    return ResponseEntity
        .internalServerError()
        .body(ApiResponse.error(500, ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
  }
}
