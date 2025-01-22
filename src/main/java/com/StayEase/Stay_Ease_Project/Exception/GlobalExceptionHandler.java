package com.StayEase.Stay_Ease_Project.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex){
        logger.error("Generic Exception occured!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("It can be one of the following errors:\n" + 
                                                                                "- Incorrect info (in the form of email or password) fed to the system.\n" + 
                                                                                "- Wrong URL\n" + 
                                                                                "- JWT has expired." + 
                                                                                "- Unauthorized access.\n" +
                                                                                "- Incorrect role assigned\n");
    }

    @ExceptionHandler(ResourceNotAvailableException.class)
    public ResponseEntity<?> resourceNotAvailableException(ResourceNotAvailableException ex){
        logger.error("Resource not favailable exception occured!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException ex){
        logger.error("Resource not favailable exception occured!");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex){
        logger.error("Resource not found exception occured!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> resourceAlreadyExistsException(ResourceAlreadyExistsException ex){
        logger.error("Resource already exists exception occured!");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());

    }
    
}
