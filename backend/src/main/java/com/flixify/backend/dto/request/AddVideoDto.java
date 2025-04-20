package com.flixify.backend.dto.request;

import lombok.Data;

@Data
public class AddVideoDto {

    private String title;

    private Double duration;

    private Long size;

    private Integer userId;
}
