package com.miguel.mybudgetplanner.exception;

import com.miguel.mybudgetplanner.response.ApiResponse;
import com.miguel.mybudgetplanner.response.ResponseUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Tratamento de exceções genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(HttpServletRequest request, Exception ex) {
        ApiResponse<Void> response = ResponseUtil.error(
                "An unexpected error occurred. Please try again later.",
                "An unexpected error occurred",
                1000,  // Código de erro genérico
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Tratamento de exceções de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiResponse<Void> response = ResponseUtil.error(
                errors,
                "Validation failed. Please check the submitted data.",
                1002,  // Código de erro de validação
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Tratamento de violações de integridade de dados, como duplicação de e-mail
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex) {
        String errorMessage = ex.getMessage();

        if (errorMessage != null && errorMessage.contains("email")) {
            ApiResponse<Void> response = ResponseUtil.error(
                    Arrays.asList("The provided email is already in use. Please use a different email."),
                    "Email already in use",
                    1004,  // Código de erro específico para e-mail duplicado
                    request.getRequestURI()
            );
            return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 CONFLICT para duplicação
        }

        if (errorMessage != null && errorMessage.contains("category_id")) {
            ApiResponse<Void> response = ResponseUtil.error(
                    Arrays.asList("The specified category does not exist."),
                    "Invalid category reference",
                    1003,  // Código de erro específico para chave estrangeira inválida
                    request.getRequestURI()
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Se for outro tipo de violação, retornar a mensagem genérica
        ApiResponse<Void> response = ResponseUtil.error(
                Arrays.asList("Data integrity violation"),
                "An error occurred while processing your request.",
                1001,
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Tratamento de exceção quando um recurso não é encontrado
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoSuchElementException(HttpServletRequest request, NoSuchElementException ex) {
        ApiResponse<Void> response = ResponseUtil.error(
                Arrays.asList("The requested resource was not found."),
                "Resource not found",
                1005,  // Código de erro específico para recurso não encontrado
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 NOT FOUND
    }
}
