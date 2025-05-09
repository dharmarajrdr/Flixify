package com.flixify.backend.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UploadVideoResponseDto extends ResponseDto {

    private String manifest;
}
