package com.flixify.backend.service.interfaces;

import com.flixify.backend.dto.response.ManifestDto;

import java.io.IOException;
import java.util.UUID;

public interface ManifestService {

    public ManifestDto getManifest(UUID fileId, Integer userId) throws IOException;

    public ManifestDto getManifestByResolution(UUID fileId, Integer userId, String resolutionName);
}
