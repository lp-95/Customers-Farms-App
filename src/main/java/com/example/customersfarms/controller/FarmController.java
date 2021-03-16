package com.example.customersfarms.controller;

import com.example.customersfarms.dto.FarmDto;
import com.example.customersfarms.service.FarmServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping( "/api/farms" )
@AllArgsConstructor
public class FarmController {
    private final FarmServiceImpl farmService;

    @GetMapping( "/get-farm/{id}" )
    @PreAuthorize( "hasRole('ADMIN')" )
    public ResponseEntity<?> getFarm( Long id ) {
        return new ResponseEntity<>( this.farmService.getFarm( id ), HttpStatus.OK );
    }

    @GetMapping( "/get-farms" )
    @PreAuthorize( "hasRole('ADMIN')" )
    public ResponseEntity<?> getFarms( @RequestParam( value = "page", defaultValue = "0" ) int page,
                                       @RequestParam( value = "size", defaultValue = "25" ) int size ) {
        return new ResponseEntity<>( this.farmService.getFarms( page, size), HttpStatus.OK );
    }

    @PostMapping( "/create-farm" )
    @PreAuthorize( "hasRole('USER')" )
    public ResponseEntity<?> createFarm( @Valid @RequestBody FarmDto dto ) {
        return new ResponseEntity<>( this.farmService.createFarm( dto ), HttpStatus.OK );
    }

    @PutMapping( "/update-farm/{id}" )
    public ResponseEntity<?> updateFarm( @Valid @PathVariable Long id, @RequestBody FarmDto dto ) {
        return new ResponseEntity<>( this.farmService.updateFarm( id, dto), HttpStatus.OK );
    }

    @DeleteMapping( "/delete-farm/{id}" )
    public ResponseEntity<?> deleteFarm( @PathVariable Long id ) {
        this.farmService.deleteFarm( id );
        return ResponseEntity.ok().build();
    }
}
