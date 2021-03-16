package com.example.customersfarms.service;

import com.example.customersfarms.dto.UserDto;
import com.example.customersfarms.exceptions.BadRequestException;
import com.example.customersfarms.exceptions.NotFoundException;
import com.example.customersfarms.model.EmailNotification;
import com.example.customersfarms.mail.MailService;
import com.example.customersfarms.model.Role;
import com.example.customersfarms.model.User;
import com.example.customersfarms.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service( value = "userService" )
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final RoleService roleService;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final TokenServiceImpl tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException( "Invalid username" ) );
        return new org.springframework.security.core.userdetails
                .User( user.getUsername(), user.getPassword(), getAuthority( user ) );
    }

    @Override
    public List<User> findUsers( int page, int size ) {
        return this.userRepository.findAll( PageRequest.of( page, size ) ).getContent();
    }

    @Override
    public User findUser( Long id ) throws NotFoundException {
        return this.userRepository.findById( id )
                .orElseThrow( () -> new NotFoundException( "Invalid id" ) );
    }

    @Override
    public void save( UserDto userDto ) {
        try {
            User newUser = new User();
            newUser.setUsername( userDto.getUsername() );
            newUser.setEmail( userDto.getEmail() );
            newUser.setPassword( this.passwordEncoder.encode( userDto.getPassword() ) );

            Role role = this.roleService.findByName( "USER" );
            Set<Role> roleSet = new HashSet<>();
            roleSet.add( role );

            if ( newUser.getEmail().split( "@" )[1].equals( "admin.edu" ) ) {
                role = this.roleService.findByName( "ADMIN" );
                roleSet.add(role);
            }

            newUser.setRoles(roleSet);

            this.userRepository.save( newUser );
            String token = this.tokenService.generateToken( newUser );

            this.mailService.send( new EmailNotification( newUser.getEmail(),
                    "Account Activation",
                    "Thank you for signing up!" +
                            "\nPlease click on the below url to activate your account: " +
                            "\nhttp://localhost:8080/api/users/verify/" + token ) );

        } catch ( DataIntegrityViolationException ex ) {
            throw new BadRequestException( "Invalid credentials" );
        }
    }

    @Override
    public void verifyAccount( String token )  throws NotFoundException {
        try {
            User user = this.tokenService.getUserFromToken( token );
            user.setEnabled( true );
            this.userRepository.save( user );
        } catch ( NotFoundException ex ) {
            throw new NotFoundException( "Invalid token" );
        }
    }

    @Override
    public User getCurrentUser() {
        User principal = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.getOne( principal.getId() );
    }

    private Set<SimpleGrantedAuthority> getAuthority( User user ) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach( role -> {
            authorities.add( new SimpleGrantedAuthority( "ROLE_" + role.getName() ) );
        } );
        return authorities;
    }


}
