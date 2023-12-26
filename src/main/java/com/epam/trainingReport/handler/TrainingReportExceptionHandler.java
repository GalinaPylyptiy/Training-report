package com.epam.trainingReport.handler;

import com.epam.trainingReport.dto.ApiExceptionDetails;
import com.epam.trainingReport.exception.InvalidJwtTokenException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class TrainingReportExceptionHandler {

    @ExceptionHandler(value = {InvalidJwtTokenException.class})
    public ResponseEntity<ApiExceptionDetails> handleInvalidJwtException(InvalidJwtTokenException ex){
        return new ResponseEntity<>(getExDetails(ex,HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<List<ApiExceptionDetails>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ApiExceptionDetails> exceptionDetailsList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            var exceptionDetail = new ApiExceptionDetails(
                    error.getDefaultMessage(),
                    HttpStatus.BAD_REQUEST,
                    LocalDateTime.now()
            );
            exceptionDetailsList.add(exceptionDetail);
        });
        return new ResponseEntity<>(exceptionDetailsList, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiExceptionDetails> handleException(Exception ex){
       return new ResponseEntity<>(getExDetails(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    private ApiExceptionDetails getExDetails(Exception ex, HttpStatus status) {
        return new ApiExceptionDetails(
                ex.getMessage(),
                status,
                LocalDateTime.now());
    }
}
