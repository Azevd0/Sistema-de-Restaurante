package com.br.davyson.GerenciamentoPedidos.exceptions;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> runTime(RuntimeException exception, HttpServletRequest request){
        StandardError standardError = new StandardError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException exception, HttpServletRequest request){
        StandardError standardError = new StandardError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException exception, HttpServletRequest request){
        StandardError standardError = new StandardError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> integrityViolation(DataIntegrityViolationException exception, HttpServletRequest request){
        StandardError standardError = new StandardError(
          LocalDateTime.now(),
          HttpStatus.CONFLICT.value(),exception.getMessage(), request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(standardError);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request){
        ValidationError validationError = new ValidationError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro na validação dos campos. Verifique os campos pendentes.",
                request.getRequestURI());
        for(FieldError error: exception.getBindingResult().getFieldErrors()){
            validationError.addError(error.getDefaultMessage(), error.getField());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> httpMessageNotReadable(HttpServletRequest request){
        StandardError standardError = new StandardError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de digitação! Revise a requisição!",
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<StandardError> optimisticLock(HttpServletRequest request){
        StandardError standardError = new StandardError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Este pedido já está aberto no aparelho de outro funcionário!" +
                        "\n Apenas um usuário por vez pode fazer alterações.",
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
}
