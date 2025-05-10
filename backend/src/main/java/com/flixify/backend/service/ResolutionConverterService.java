package com.flixify.backend.service;

import com.flixify.backend.model.Resolution;

import java.io.File;
import java.io.IOException;

public interface ResolutionConverterService {

    public void convertResolution(Resolution resolution, File source, File target) throws IOException, InterruptedException;
}
