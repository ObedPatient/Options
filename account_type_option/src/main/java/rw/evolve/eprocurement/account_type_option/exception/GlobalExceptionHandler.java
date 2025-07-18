/**
 * Handles all exceptions globally in the application.
 */
package rw.evolve.eprocurement.account_type_option.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rw.evolve.eprocurement.account_type_option.dto.ResponseMessageDto;

import java.time.LocalDateTime;

public class GlobalExceptionHandler {
    /**
     * Handles any exception and returns a standardized error response.
     *
     * @param e the exception thrown
     * @return ResponseEntity with ErrorResponseDto as Object.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalExceptions(Exception e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseMessageDto(
                        "Internal Server Error: " + e.getMessage(),
                        "Error",
                        500,
                        LocalDateTime.now()
                ));
    }
}
