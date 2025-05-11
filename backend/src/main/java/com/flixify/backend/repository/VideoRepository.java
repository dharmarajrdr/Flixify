package com.flixify.backend.repository;

import com.flixify.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flixify.backend.model.Video;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    public List<Video> findByOwner(User user);

    public Optional<Video> findByFileId(UUID fileId);

    public Boolean existsByFileIdAndOwner(UUID fileId, User owner);

    public List<Video> findByIsDeletedTrueAndLastUpdatedAtBefore(LocalDateTime cutoffDate);
}
