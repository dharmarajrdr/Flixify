package com.flixify.backend.controller;

import com.flixify.backend.dto.request.AddVideoDto;
import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import com.flixify.backend.model.Video;
import com.flixify.backend.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/videos")
    public ResponseEntity<ResponseDto> getAllVideos(@RequestParam Integer userId) {

        List<Video> videos = videoService.getVideosByUserId(userId);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the videos of the given user.", videos, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/videos/{videoId}")
    public ResponseEntity<ResponseDto> getVideoInfo(@RequestParam Integer userId, @PathVariable Integer videoId) {

        Video video = videoService.getVideo(userId, videoId);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the video info.", video, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/video")
    public ResponseEntity<ResponseDto> addVideo(@RequestBody AddVideoDto addVideoDto) {

        Video video = videoService.addVideo(addVideoDto);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Stored the video successfully.", video, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
