package com.flixify.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flixify.backend.model.Resolution;

@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Integer> {

}
