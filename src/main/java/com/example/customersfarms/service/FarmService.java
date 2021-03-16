package com.example.customersfarms.service;

import com.example.customersfarms.dto.FarmDto;
import com.example.customersfarms.dto.FarmResponse;
import com.example.customersfarms.exceptions.BadRequestException;
import com.example.customersfarms.exceptions.NotFoundException;
import com.example.customersfarms.model.Farm;

import java.util.List;
import java.util.UUID;

public interface FarmService {
    Farm getFarm( Long id ) throws NotFoundException;
    List<FarmResponse> getFarms( int page, int size );
    FarmResponse createFarm( FarmDto farmDto ) throws BadRequestException;
    FarmResponse updateFarm( Long id, FarmDto farmDto ) throws BadRequestException, NotFoundException;
    void deleteFarm( Long id ) throws NotFoundException;
}
