package com.flixify.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flixify.backend.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

}
