package com.flixify.backend.dto.request;

import lombok.Data;

@Data
public class AddChunkDto {

    private Integer pixel;

    private Double startTime;

    private Double endTime;

    private String fileId;    // UUID = Name of the file

    private String chunkStatus;

    private Double size;

}
