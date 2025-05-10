package com.flixify.backend.service.interfaces;

import com.flixify.backend.model.Resolution;

public interface VideoDownscaleService {

    public void downgrade(Resolution resolution);
}
