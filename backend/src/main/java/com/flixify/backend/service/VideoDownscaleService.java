package com.flixify.backend.service;

import com.flixify.backend.model.Resolution;

public interface VideoDownscaleService {

    public void downgrade(Resolution resolution);
}
