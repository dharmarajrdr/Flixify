package com.flixify.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flixify.backend.model.UploadStatus;

@Repository
public interface UploadStatusRepository extends JpaRepository<UploadStatus, Integer> {

}
