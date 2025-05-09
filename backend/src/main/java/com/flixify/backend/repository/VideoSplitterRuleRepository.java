package com.flixify.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flixify.backend.model.VideoSplitterRule;

import java.util.Optional;

@Repository
public interface VideoSplitterRuleRepository extends JpaRepository<VideoSplitterRule, Integer> {

    public Optional<VideoSplitterRule> findByName(String name);
}
