package com.flixify.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flixify.backend.model.VideoSplitterRule;

@Repository
public interface VideoSplitterRuleRepository extends JpaRepository<VideoSplitterRule, Integer> {

}
