package com.flixify.backend.service.interfaces;

import com.flixify.backend.model.Resolution;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ResolutionService {

    public List<Resolution> getAllResolutionsLessThanPixel(int pixel);

    public Resolution getResolutionByTitle(String title);

    public Resolution getFileResolution(File file) throws IOException;

    public File transcodeChunks(Resolution resolutionToTranscode, UUID uniqueId, Resolution rawResolution);
}
