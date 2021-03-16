package com.example.customersfarms.controller;

import com.example.customersfarms.dto.FarmDto;
import com.example.customersfarms.dto.FarmResponse;
import com.example.customersfarms.model.Farm;
import com.example.customersfarms.model.User;
import com.example.customersfarms.service.FarmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FarmControllerTest {
    @InjectMocks
    FarmController farmController;
    @Mock
    FarmServiceImpl farmService;

    Farm farm;
    FarmDto farmDto;
    FarmResponse farmResponse;
    User user;
    List<FarmResponse> farmResponses;

    final Long id = 1L;
    final String name = "name";
    final String description = "description";
    final int page = 0;
    final int size = 25;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks( this );

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

        this.farmResponses = new ArrayList<>();
        this.farmResponses.add( this.farmResponse );
    }

    @Test
    void getFarm() {
        when( this.farmService.getFarm( this.id ) )
                .thenReturn( this.farm );
        ResponseEntity<?> responseEntity = this.farmController.getFarm( this.id );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void getFarms() {
        when( this.farmService.getFarms( this.page, this.size ) )
                .thenReturn( this.farmResponses );
        ResponseEntity<?> responseEntity = this.farmController.getFarms( this.page, this.size );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void createFarm() {
        when( this.farmService.createFarm( this.farmDto ) )
                .thenReturn( this.farmResponse );
        ResponseEntity<?> responseEntity = this.farmController.createFarm( this.farmDto );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void updateFarm() {
        when( this.farmService.updateFarm( this.id, this.farmDto ) )
                .thenReturn( this.farmResponse );
        ResponseEntity<?> responseEntity = this.farmController.updateFarm( this.id, this.farmDto );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void deleteFarm() {
        ResponseEntity<?> responseEntity = this.farmController.deleteFarm( this.id );
        assertEquals( responseEntity.getStatusCode(), HttpStatus.OK );
    }
}