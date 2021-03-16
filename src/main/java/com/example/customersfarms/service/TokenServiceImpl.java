package com.example.customersfarms.service;

import com.example.customersfarms.dto.TokenDto;
import com.example.customersfarms.exceptions.NotFoundException;
import com.example.customersfarms.model.Token;
import com.example.customersfarms.model.User;
import com.example.customersfarms.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public Token findToken( String token ) throws NotFoundException {
        return this.tokenRepository.findByToken( token )
                .orElseThrow( () -> new NotFoundException( "Invalid token" ) );
    }

    @Override
    public String generateToken( User user ) {
        Token token = new Token();
        token.setToken( UUID.randomUUID().toString() );
        token.setUser( user );
        this.tokenRepository.save( token );
        return token.getToken();
    }

    @Override
    public User getUserFromToken( String token ) throws NotFoundException {
        return this.findToken( token ).getUser();
    }

    @Override
    public TokenDto refreshToken( Token token ) {
        this.tokenRepository.save( token );
        return new TokenDto( token.getToken(), token.getUser().getUsername() );
    }

    @Override
    public void deleteToken( String token ) throws NotFoundException {
        this.tokenRepository.delete( this.findToken( token ) );
    }
}
