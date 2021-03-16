package com.example.customersfarms.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class BadRequestException extends DataIntegrityViolationException {

    public BadRequestException( String msg ) {
        super( msg);
    }

    public BadRequestException( String msg, Throwable cause ) {
        super( msg, cause );
    }
}
