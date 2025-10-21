package com.example.demo.controller.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record NewWorkstationDTO(
    @NotBlank
    @Length(max = 255)
    String specs
) {
}
