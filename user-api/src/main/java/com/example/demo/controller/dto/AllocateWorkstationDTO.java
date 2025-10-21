package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AllocateWorkstationDTO(

    @NotNull(message = "userId cannot be null")
    @Positive(message = "userId must be a positive integer")
    Integer userId
) {

}
