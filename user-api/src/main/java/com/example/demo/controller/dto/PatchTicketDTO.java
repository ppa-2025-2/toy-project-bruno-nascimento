package com.example.demo.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatchTicketDTO(
    @NotNull(message = "O Resposavel")
    Integer manager_id,
    @NotNull(message = "status nao pode ser null")
    @NotBlank(message = "Status nao pode conter espaços")
    String status,
    String cancel_reason
)  {

}
