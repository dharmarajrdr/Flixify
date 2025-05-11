package com.flixify.backend.service.interfaces;

import com.flixify.backend.model.Video;

public interface VideoDeleter {

    public void delete(Video video);

    public void recover(Video video);
}
