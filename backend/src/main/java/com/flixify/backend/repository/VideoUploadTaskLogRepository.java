package com.flixify.backend.repository;

import com.flixify.backend.model.VideoUploadTaskLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoUploadTaskLogRepository extends JpaRepository<VideoUploadTaskLog, Long> {
}
