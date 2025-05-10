package com.flixify.backend.repository;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.MediaDownloads;
import com.flixify.backend.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaDownloadsRepository extends JpaRepository<MediaDownloads, Long> {

    public List<MediaDownloads> findAllByVideo(Video video);

    public Optional<MediaDownloads> findByVideoAndChunk(Video video, Chunk chunk);
}
