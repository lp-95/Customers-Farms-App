package com.example.customersfarms.controller;

import com.example.customersfarms.dto.LoginUser;
import com.example.customersfarms.dto.TokenDto;
import com.example.customersfarms.dto.UserDto;
import com.example.customersfarms.service.AuthServiceImpl;
import com.example.customersfarms.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequestMapping( "/api/users" )
@AllArgsConstructor
public class UserController {
    private final AuthServiceImpl authService;
    private final UserServiceImpl userService;

    @GetMapping( "/user/{id}" )
    @PreAuthorize( "hasRole('ADMIN')" )
    public ResponseEntity<?> findUser( @PathVariable Long id ) {
        return new ResponseEntity<>( this.userService.findUser( id ), HttpStatus.OK );
    }

    @GetMapping
    @PreAuthorize( "hasRole('ADMIN')" )
    public ResponseEntity<?> findUsers( @RequestParam( value = "page", defaultValue = "0" ) int page,
                                        @RequestParam( value =  "size", defaultValue = "25" ) int size ) {
        return new ResponseEntity<>( this.userService.findUsers( page, size), HttpStatus.OK );
    }

    @PostMapping( "/register" )
    public ResponseEntity<?> register( @Valid @RequestBody UserDto user ) {
        this.userService.save( user );
        return ResponseEntity.ok().build();
    }

    @GetMapping( "/verify/{token}" )
    public ResponseEntity<?> verifyAccount( @PathVariable String token ) {
        this.userService.verifyAccount( token );
        return ResponseEntity.ok().build();
    }

    @PostMapping( "/authenticate" )
    public ResponseEntity<?> logIn( @Valid @RequestBody LoginUser loginUser ) throws AuthenticationException {

        return ResponseEntity.ok( this.authService.logIn( loginUser ) );
    }

    @PostMapping( "/token-refresh" )
    @PreAuthorize( "hasRole('USER')" )
    public ResponseEntity<?> refreshToken( @Valid @RequestBody TokenDto tokenDto ) {
        return new ResponseEntity<>( this.authService.refreshToken( tokenDto ), HttpStatus.OK );
    }

    @PostMapping( "logout" )
    @PreAuthorize( "hasRole('USER')" )
    public ResponseEntity<?> logout( @Valid @RequestBody TokenDto tokenDto ) {
        this.authService.logOut( tokenDto );
        return ResponseEntity.ok().build();
    }
}
