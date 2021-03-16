package com.example.customersfarms.service;

import com.example.customersfarms.dto.TokenDto;
import com.example.customersfarms.dto.LoginUser;

public interface AuthService {
    TokenDto logIn( LoginUser loginUser );
    TokenDto refreshToken( TokenDto tokenDto );
    void logOut( TokenDto token );
    boolean isLoggedIn();
}
