package com.example.demo.controller.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.example.demo.repository.entity.Island;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewIslandDTO(

    @NotBlank(message = "Description cannot be blank")
    @Length(max = 255,message = "Description cannot exceed 255 characters")
    String description,

    @NotNull(message = "Island Disposition cannot be null(Should be CIRCULAR, SQUARE, RECTANGULAR, TRIANGULAR)")
    Island.Disposition disposition,

    @NotEmpty(message = "Workstations cannot be empty")
    List<@Valid NewWorkstationDTO> workstations
)  {
}
