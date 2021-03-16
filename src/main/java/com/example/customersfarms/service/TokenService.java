package com.example.customersfarms.service;

import com.example.customersfarms.dto.TokenDto;
import com.example.customersfarms.exceptions.NotFoundException;
import com.example.customersfarms.model.Token;
import com.example.customersfarms.model.User;

public interface TokenService {
    Token findToken( String token ) throws NotFoundException;
    String generateToken( User user );
    User getUserFromToken( String token );
    TokenDto refreshToken( Token token );
    void deleteToken( String token );
}
