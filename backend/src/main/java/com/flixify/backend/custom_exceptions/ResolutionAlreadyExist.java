package com.flixify.backend.custom_exceptions;

import com.flixify.backend.model.Resolution;

import java.io.File;

public class ResolutionAlreadyExist extends RuntimeException {

    public ResolutionAlreadyExist(Resolution resolution, File file) {

        super("Resolution " + resolution.getTitle() + " already exists.");
    }
}
