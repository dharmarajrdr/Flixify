package com.flixify.backend.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Resolution extends BaseModel {

    private String title;

    private Integer pixel;

    public Resolution(String title, Integer pixel) {
        this.title = title;
        this.pixel = pixel;
    }
}
