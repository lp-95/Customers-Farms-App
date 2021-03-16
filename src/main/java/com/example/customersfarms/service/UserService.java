package com.example.customersfarms.service;

import com.example.customersfarms.dto.UserDto;
import com.example.customersfarms.exceptions.NotFoundException;
import com.example.customersfarms.model.User;

import java.util.List;

public interface UserService {
    User findUser( Long id ) throws NotFoundException;
    void save( UserDto user );
    List<User> findUsers( int page, int size );
    void verifyAccount( String token );
    User getCurrentUser();
}
