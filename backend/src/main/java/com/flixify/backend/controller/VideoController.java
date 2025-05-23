package com.flixify.backend.controller;

import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.dto.response.VideoDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import com.flixify.backend.model.Video;
import com.flixify.backend.service.interfaces.VideoService;
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
            if (video.isDeleted()) {
                continue;
            }
            videoDtos.add(VideoDto.fromVideo(video));
        }
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the videos of the given user.", videoDtos, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/trash")
    public ResponseEntity<ResponseDto> getAllDeletedVideos(@RequestParam Integer userId) {

        List<Video> deletedVideos = videoService.getVideosInTrashByUserId(userId);
        List<VideoDto> videoDtos = new ArrayList<>();
        for (Video video : deletedVideos) {
            videoDtos.add(VideoDto.fromVideo(video));
        }
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the videos from trash.", videoDtos, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<ResponseDto> getVideoInfo(@RequestParam Integer userId, @PathVariable String fileId) {

        VideoDto video = VideoDto.fromVideo(videoService.getVideo(userId, UUID.fromString(fileId)));
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the video info.", video, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<ResponseDto> deleteVideo(@RequestParam Integer userId, @PathVariable UUID fileId) {

        videoService.deleteVideo(userId, fileId);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Deleted the video.", null, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/recover/{fileId}")
    public ResponseEntity<ResponseDto> recoverVideo(@RequestParam Integer userId, @PathVariable UUID fileId) {

        videoService.recoverVideo(userId, fileId);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Video recovered from trash successfully.", null, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
