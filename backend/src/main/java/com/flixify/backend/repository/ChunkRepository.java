package com.flixify.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Video;

@Repository
public interface ChunkRepository extends JpaRepository<Chunk, Integer> {

    public List<Chunk> findByVideo(Video video);
}
