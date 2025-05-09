package com.flixify.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flixify.backend.model.ChunkStatus;

import java.util.Optional;

@Repository
public interface ChunkStatusRepository extends JpaRepository<ChunkStatus, Integer> {

    Optional<ChunkStatus> findByStatus(String status);
}
