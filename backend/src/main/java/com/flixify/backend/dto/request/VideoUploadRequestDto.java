package com.flixify.backend.dto.request;

import lombok.Data;

@Data
public class VideoUploadRequestDto {

    private String title;

    private Integer userId;
}
