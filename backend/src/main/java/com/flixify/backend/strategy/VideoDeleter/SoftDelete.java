package com.flixify.backend.strategy.VideoDeleter;

import com.flixify.backend.model.Video;
import com.flixify.backend.repository.VideoRepository;
import com.flixify.backend.service.interfaces.VideoDeleterService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@AllArgsConstructor
/**
 * Toggle the status of video as deleted. Recover later by reverting the same.
 */
public class SoftDelete implements VideoDeleterService {

    private final VideoRepository videoRepository;

    @Override
    public void delete(Video video) {

        video.setDeleted(true);
        videoRepository.save(video);
    }

    @Override
    public void recover(Video video) {

        video.setDeleted(false);
        videoRepository.save(video);
    }
}
