package com.example.customersfarms.service;

import com.example.customersfarms.dto.FarmDto;
import com.example.customersfarms.dto.FarmResponse;
import com.example.customersfarms.exceptions.BadRequestException;
import com.example.customersfarms.exceptions.NotFoundException;
import com.example.customersfarms.model.Farm;
import com.example.customersfarms.repository.FarmRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;
    private final UserServiceImpl userService;

    @Override
    public Farm getFarm( Long id ) throws NotFoundException {
        return this.farmRepository.findById( id )
                .orElseThrow( () -> new NotFoundException( "Invalid ID" ) );
    }

    @Override
    public List<FarmResponse> getFarms( int page, int size ) {
        List<FarmResponse> farmResponses = new ArrayList<>();
        List<Farm> farms = this.farmRepository.findAll( PageRequest.of( page, size ) ).getContent();
        for ( Farm farm : farms ) {
            farmResponses.add(
                    new FarmResponse( farm.getId(), farm.getName(), farm.getDescription(), farm.getUser().getId() ) );
        }
        return farmResponses;
    }

    @Override
    public FarmResponse createFarm( FarmDto farmDto ) throws BadRequestException {
        Farm farm;
        try {
            farm = new Farm();
            farm.setName( farmDto.getName() );
            farm.setDescription( farmDto.getDescription() );
            farm.setUser( this.userService.getCurrentUser() );
            this.farmRepository.save( farm );
        } catch ( DataIntegrityViolationException ex ) {
            throw new BadRequestException( "Name already exist" );
        }
        return new FarmResponse( farm.getId(), farm.getName(), farm.getDescription(), farm.getUser().getId() );
    }

    @Override
    public FarmResponse updateFarm( Long id, FarmDto farmDto ) throws BadRequestException, NotFoundException {
        Farm farm = this.getFarm( id );;
        farm.setName( farmDto.getName() );
        farm.setDescription( farmDto.getDescription() );
        farm.setUser( this.userService.getCurrentUser() );
        try {
            this.farmRepository.save( farm );
        } catch ( BadRequestException ex ) {
            throw new BadRequestException( "Name already exist" );
        }
        return new FarmResponse( farm.getId(), farm.getName(), farm.getDescription(), farm.getUser().getId() );
    }

    @Override
    public void deleteFarm( Long id ) throws NotFoundException {
        this.farmRepository.delete( this.getFarm( id ) );
    }
}
