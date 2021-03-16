package com.example.customersfarms.service;

import com.example.customersfarms.dto.UserDto;
import com.example.customersfarms.exceptions.BadRequestException;
import com.example.customersfarms.exceptions.NotFoundException;
import com.example.customersfarms.mail.MailService;
import com.example.customersfarms.model.Role;
import com.example.customersfarms.model.Token;
import com.example.customersfarms.model.User;
import com.example.customersfarms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    TokenServiceImpl tokenService;

    UserDto userDto;
    User user;
    User principal;
    Role role;
    Token tokenObj;
    SimpleGrantedAuthority authority;
    Set<SimpleGrantedAuthority> authorities;
    org.springframework.security.core.userdetails.User userDetails;

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
    }

    @Test
    void loadUserByUsername_whenUserWithGivenUsernameExist_returnUserDetails() {
        when( this.userRepository.findByUsername( this.username ) )
                .thenReturn( java.util.Optional.ofNullable( this.user ) );
    }

    @Test
    void loadUserByUsername_whenUserWithGivenUsernameNotExist_throwsException() {
        when( this.userRepository.findByUsername( this.username ) )
                .thenThrow( UsernameNotFoundException.class );
        assertThrows( UsernameNotFoundException.class, () -> {
            this.userService.loadUserByUsername( this.username );
        }  );
    }

    @Test
    void findUser_whenUserWithGivenIdExist_returnUser() {
        when( this.userRepository.findById( this.id ) )
                .thenReturn( java.util.Optional.ofNullable( this.user ) );
        assertEquals( this.userService.findUser( this.id ).getId(), this.id );
    }

    @Test
    void findUser_whenUserWithGivenIdNotExist_throwsException() {
        when( this.userRepository.findById( this.id ) )
                .thenThrow( NotFoundException.class );
        assertThrows( NotFoundException.class, () -> {
            this.userService.findUser( this.id );
        } );
    }

    @Test
    void findUsers_whenDatabaseContainsUsers_returnListOfUsers_orEmptyList() {
        Pageable pageable = PageRequest.of( this.page, this.size );
        Page<User> usersPage = this.userRepository.findAll( pageable );
        when( this.userRepository.findAll( pageable ) )
                .thenReturn( usersPage );
    }

    @Test
    void save_whenUserWithGivenCredentialsExist_throwsException() {
        when( this.userRepository.save( this.user ) )
                .thenThrow( DataIntegrityViolationException.class );
        try {
            this.userService.save( this.userDto );
        } catch ( BadRequestException ex ) {
            ex.printStackTrace();
        }
    }

    @Test
    void save_whenUserWithGivenCredentialsNotExist_userSaveSuccessful() {
        when( this.userRepository.save( this.user ) )
                .thenReturn( this.user );
    }

    @Test
    void verifyAccount_whenTokenIsCorrect() {
        when( this.tokenService.findToken( this.token ) )
                .thenReturn( this.tokenObj );
        User user = this.tokenObj.getUser();
        user.setEnabled( true );
    }

    @Test
    void verifyAccount_whenTokenIsNotCorrect() {
        when( this.tokenService.findToken( this.token ) )
            .thenThrow( NotFoundException.class );
    }

    @Test
    void getCurrentUser() {
        when( this.userRepository.getOne( this.id ) )
                .thenReturn( this.principal );
        assertEquals( this.principal, this.user );
    }
}