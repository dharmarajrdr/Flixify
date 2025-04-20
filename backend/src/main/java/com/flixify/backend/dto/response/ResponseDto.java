package com.flixify.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.flixify.backend.enums.ResponseStatusEnum;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    private String message;

    private Object data;

    private Object info;

    private ResponseStatusEnum status;

    public ResponseDto(ResponseStatusEnum status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseDto(ResponseStatusEnum status, String message, Object data, Object info) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.info = info;
    }
}
