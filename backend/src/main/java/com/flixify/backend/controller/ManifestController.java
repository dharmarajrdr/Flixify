package com.flixify.backend.controller;

import com.flixify.backend.dto.response.ManifestDto;
import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import com.flixify.backend.service.interfaces.ManifestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/manifest")
public class ManifestController {

    @Autowired
    private ManifestService manifestService;

    @GetMapping("/video/{fileId}")
    public ResponseEntity<ResponseDto> getManifestFile(@PathVariable UUID fileId, @RequestParam Integer userId) throws IOException {

        ManifestDto manifest = manifestService.getManifest(fileId, userId);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the manifest json successfully.", manifest, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/video/{fileId}/resolution/{resolution}")
    public ResponseEntity<ResponseDto> getManifestFileByResolution(@PathVariable UUID fileId, @PathVariable String resolution, @RequestParam Integer userId) throws IOException {

        ManifestDto manifest = manifestService.getManifestByResolution(fileId, userId, resolution);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the manifest json successfully.", manifest, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
