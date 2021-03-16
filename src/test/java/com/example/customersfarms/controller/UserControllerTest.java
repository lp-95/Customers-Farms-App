package com.example.customersfarms.controller;

import com.example.customersfarms.dto.LoginUser;
import com.example.customersfarms.dto.TokenDto;
import com.example.customersfarms.dto.UserDto;
import com.example.customersfarms.model.Role;
import com.example.customersfarms.model.Token;
import com.example.customersfarms.model.User;
import com.example.customersfarms.service.AuthServiceImpl;
import com.example.customersfarms.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTest {
    @InjectMocks
    UserController userController;
    @Mock
    UserServiceImpl userService;
    @Mock
    AuthServiceImpl authService;

    UserDto userDto;
    User user;
    User principal;
    List<User> users;
    Role role;
    Token tokenObj;
    SimpleGrantedAuthority authority;
    Set<SimpleGrantedAuthority> authorities;
    org.springframework.security.core.userdetails.User userDetails;
    LoginUser loginUser;
    TokenDto tokenDto;
    TokenDto refreshed;

    final Long id = 1L;
    final String username = "test";
    final String password = "password";
    final String email = "test@email.com";
    final boolean enabled = false;
    final String roleAdmin = "ADMIN";
    final String roleUser = "USER";
    final String description = "desc";
    final String token = "token";
    final int page = 0;
    final int size = 30;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this );

        this.userDto = new UserDto();
        this.userDto.setUsername( this.username );
        this.userDto.setPassword( this.password );
        this.userDto.setEmail( this.email );

        this.user = new User();
        this.user.setId( this.id );
        this.user.setUsername( this.userDto.getUsername() );
        this.user.setPassword( this.userDto.getPassword() );
        this.user.setEmail( this.userDto.getEmail() );
        this.user.setEnabled( this.enabled );

        this.role = new Role();
        this.role.setId( this.id );
        this.role.setName( this.roleUser );
        this.role.setDescription( this.description );

        this.tokenObj = new Token();
        this.tokenObj.setId( this.id );
        this.tokenObj.setToken( this.token );
        this.tokenObj.setUser( this.user );

        this.authority = new SimpleGrantedAuthority( this.roleUser );
        this.authorities = new HashSet<>();
        this.authorities.add( this.authority );

        this.userDetails = new org.springframework.security.core.userdetails.User(
                this.username,
                this.password,
                this.authorities );

        this.principal = this.user;

        this.users = new ArrayList<>();
        this.users.add( this.user );

        this.loginUser = new LoginUser();
        this.loginUser.setUsername( this.username );
        this.loginUser.setPassword( this.password );

        this.tokenDto = new TokenDto();
        this.tokenDto.setToken( this.token );
        this.tokenDto.setUsername( this.username );

        this.refreshed = new TokenDto();
        this.refreshed.setToken( this.token + this.token );
        this.refreshed.setUsername( this.username );
    }

    @Test
    void findUser() {
        when( this.userService.findUser( this.id ) )
                .thenReturn( this.user );
        ResponseEntity<?> responseEntity = this.userController.findUser( id );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void findUsers() {
        when( this.userService.findUsers( this.page, this.size ) )
                .thenReturn( this.users );
        ResponseEntity<?> responseEntity = this.userController.findUsers( this.page, this.size );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void register() {
        ResponseEntity<?> responseEntity = this.userController.register( this.userDto );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void verifyAccount() {
        ResponseEntity<?> responseEntity = this.userController.verifyAccount( this.token );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void logIn() {
        when( this.authService.logIn( this.loginUser ) )
                .thenReturn( this.tokenDto );
        ResponseEntity<?> responseEntity = this.userController.logIn( this.loginUser );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void refreshToken() {
        when( this.authService.refreshToken( this.tokenDto ) )
                .thenReturn( this.tokenDto );
        ResponseEntity<?> responseEntity = this.userController.logIn( this.loginUser );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void logout() {
        ResponseEntity<?> responseEntity = this.userController.logout( this.tokenDto );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }
}