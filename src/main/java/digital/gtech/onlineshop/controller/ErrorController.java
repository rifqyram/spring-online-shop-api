package digital.gtech.onlineshop.controller;

import digital.gtech.onlineshop.util.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        log.error("Error: {}", e.getMessage());
        return ResponseUtil.buildResponse(
                HttpStatus.valueOf(e.getStatusCode().value()),
                e.getReason(),
                null
        );
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Error bad request: {}", e.getMessage());
        return ResponseUtil.buildResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null
        );
    }
}
