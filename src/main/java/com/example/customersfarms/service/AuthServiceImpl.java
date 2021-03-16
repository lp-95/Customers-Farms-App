package com.example.customersfarms.service;

import com.example.customersfarms.dto.TokenDto;
import com.example.customersfarms.dto.LoginUser;
import com.example.customersfarms.model.Token;
import com.example.customersfarms.security.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenServiceImpl tokenService;
    private final TokenProvider tokenProvider;

    @Override
    public TokenDto logIn( LoginUser loginUser ) {
        final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( loginUser.getUsername(), loginUser.getPassword() ) );
        SecurityContextHolder.getContext().setAuthentication( authentication );
        final String token = this.tokenProvider.generateToken( authentication );
        return new TokenDto( token, loginUser.getUsername() );
    }

    @Override
    public TokenDto refreshToken( TokenDto tokenDto ) {
        Token token = this.tokenService.findToken( tokenDto.getToken() );
        String newToken = this.tokenProvider.generateTokenFromUsername( tokenDto.getUsername() );
        token.setToken( newToken );
        return this.tokenService.refreshToken( token );
    }

    @Override
    public void logOut( TokenDto tokenDto ) {
        this.tokenService.deleteToken( tokenDto.getToken() );
    }

    @Override
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !( authentication instanceof AnonymousAuthenticationToken ) && authentication.isAuthenticated();
    }
}
