package com.example.customersfarms.service;

import com.example.customersfarms.dto.FarmDto;
import com.example.customersfarms.dto.FarmResponse;
import com.example.customersfarms.exceptions.BadRequestException;
import com.example.customersfarms.exceptions.NotFoundException;
import com.example.customersfarms.model.Farm;
import com.example.customersfarms.model.User;
import com.example.customersfarms.repository.FarmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FarmServiceImplTest {
    @InjectMocks
    FarmServiceImpl farmService;
    @Mock
    FarmRepository farmRepository;
    @Mock
    UserServiceImpl userService;

    Farm farm;
    FarmDto farmDto;
    FarmResponse farmResponse;
    User user;

    final Long id = 1L;
    final String name = "name";
    final String description = "description";
    final int page = 0;
    final int size = 25;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this );

        this.user = new User();
        this.user.setId( this.id );
        this.user.setUsername( "username" );
        this.user.setPassword( "password" );
        this.user.setEmail( "email" );

        this.farm = new Farm();
        this.farm.setId( this.id );
        this.farm.setName( this.name );
        this.farm.setDescription( this.description );
        this.farm.setUser( this.user );

        this.farmDto = new FarmDto();
        this.farmDto.setName( this.name );
        this.farmDto.setDescription( this.description );

        this.farmResponse = new FarmResponse();
        this.farmResponse.setId( this.id );
        this.farmResponse.setName( this.name );
        this.farmResponse.setDescription( this.description );
        this.farmResponse.setUserId( this.id );
    }

    @Test
    void getFarm_whenFarmIdExist_returnFarm() {
        when( this.farmRepository.findById( this.id ) )
                .thenReturn( java.util.Optional.ofNullable( this.farm ) );
        assertEquals( this.farmService.getFarm( this.id ), this.farm );
    }

    @Test
    void getFarm_whenFarmIdNotExist_throwsException() {
        when( this.farmRepository.findById( this.id ) )
                .thenThrow( NotFoundException.class );
        assertThrows( NotFoundException.class, () -> {
            this.farmService.getFarm( this.id );
        } );
    }

    @Test
    void getFarms_returnEmptyListOrListOfFarms() {
        Pageable pageable = PageRequest.of( this.page, this.size );
        Page<Farm> candidatePage = this.farmRepository.findAll( pageable );
        when( this.farmRepository.findAll( pageable ) )
                .thenReturn( candidatePage );
    }


    @Test
    void createFarm_whenFarmWithGivenNameExist_throwsException() {
        when( this.farmRepository.save( this.farm ) )
                .thenThrow( DataIntegrityViolationException.class );
        try {
            this.farmService.createFarm( this.farmDto );
        } catch ( BadRequestException ex ) {
            ex.printStackTrace();
        }
    }

    @Test
    void createFarm_whenFarmWithGivenNameNotExist_successful() {
        when( this.farmRepository.save( this.farm ) )
                .thenReturn( this.farm );
    }

    @Test
    void updateFarm_whenIdNotExist_throwsException() {
        when( this.farmRepository.findById( this.id ) )
                .thenThrow( NotFoundException.class );
    }

    @Test
    void updateFarm_whenIdExist_successful() {
        when( this.farmRepository.findById( this.id ) )
                .thenReturn( java.util.Optional.ofNullable( this.farm ) );
    }

    @Test
    void deleteFarm() {
        when( this.farmRepository.findById( this.id ) )
                .thenReturn( java.util.Optional.ofNullable( this.farm ) );
    }
}