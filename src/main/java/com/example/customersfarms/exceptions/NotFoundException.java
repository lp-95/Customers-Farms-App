package com.example.customersfarms.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class NotFoundException extends DataIntegrityViolationException {

    public NotFoundException( String msg ) {
        super( msg );
    }

    public NotFoundException( String msg, Throwable cause ) {
        super( msg, cause );
    }
}
