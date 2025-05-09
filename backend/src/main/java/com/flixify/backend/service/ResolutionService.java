package com.flixify.backend.service;

import com.flixify.backend.custom_exceptions.ResolutionNotFound;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.repository.ResolutionRepository;
import org.springframework.stereotype.Service;

@Service
public class ResolutionService {

    private ResolutionRepository resolutionRepository;

    public ResolutionService(ResolutionRepository resolutionRepository) {
        this.resolutionRepository = resolutionRepository;
    }

    public Resolution getResolution(Integer pixel) {

        return resolutionRepository.findByPixel(pixel).orElseThrow(()-> new ResolutionNotFound(pixel));
    }
}
