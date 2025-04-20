package com.flixify.backend.controller;

import com.flixify.backend.custom_exceptions.UserNotFound;
import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import com.flixify.backend.model.Video;
import com.flixify.backend.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VideoController {

    private VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/video/{userId}")
    public ResponseEntity<ResponseDto> getAllVideos(@PathVariable Integer userId) throws UserNotFound {

        List<Video> videos = videoService.getVideosByUserId(userId);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the videos of the given user.", videos, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
