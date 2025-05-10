package com.flixify.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flixify.backend.model.Resolution;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Integer> {

    Optional<Resolution> findByPixel(Integer pixel);

    @Query("SELECT r FROM Resolution r WHERE r.pixel <= :pixel ORDER BY r.pixel DESC")
    List<Resolution> findResolutionByPixelLessThanLimit(@Param("pixel") int pixel);
}
