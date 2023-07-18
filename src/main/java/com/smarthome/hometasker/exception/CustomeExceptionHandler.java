package com.smarthome.hometasker.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomeExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomeExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setException("Processing failed");
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setTimestamp(new Date());
        logger.info("Error:" + errorDetails);
        return new ResponseEntity<Object>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
