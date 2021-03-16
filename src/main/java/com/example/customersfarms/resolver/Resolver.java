package com.example.customersfarms.resolver;

import com.example.customersfarms.exceptions.BadRequestException;
import com.example.customersfarms.exceptions.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order( Ordered.HIGHEST_PRECEDENCE )
@RestControllerAdvice
public class Resolver extends ResponseEntityExceptionHandler {

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( value = { BadRequestException.class } )
    public ResponseEntity<?> badRequestResolver( BadRequestException ex ) {
        return new ResponseEntity<>( ex, new HttpHeaders(), HttpStatus.BAD_REQUEST );
    }

    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ExceptionHandler( value = { NotFoundException.class } )
    public ResponseEntity<?> notFoundException( NotFoundException ex ) {
        return new ResponseEntity<>( ex, new HttpHeaders(), HttpStatus.NOT_FOUND );
    }
}
