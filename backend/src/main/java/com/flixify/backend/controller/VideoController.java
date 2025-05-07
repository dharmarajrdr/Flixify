package com.flixify.backend.controller;

import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.dto.response.VideoDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import com.flixify.backend.model.Video;
import com.flixify.backend.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto> getAllVideos(@RequestParam Integer userId) {

        List<Video> videos = videoService.getVideosByUserId(userId);
        List<VideoDto> videoDtos = new ArrayList<>();
        for (Video video : videos) {
            videoDtos.add(VideoDto.fromVideo(video));
        }
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the videos of the given user.", videoDtos, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<ResponseDto> getVideoInfo(@RequestParam Integer userId, @PathVariable String fileId) {

        VideoDto video = VideoDto.fromVideo(videoService.getVideo(userId, UUID.fromString(fileId)));
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the video info.", video, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    
}
