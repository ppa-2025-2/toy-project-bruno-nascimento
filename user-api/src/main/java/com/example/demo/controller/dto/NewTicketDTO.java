package com.example.demo.controller.dto;

/*
 * -- C -> Mother of All Languages
 * -- Turing Complete Language
 * -- LINGUAGEM DECLARATIVA (linguagem de pedidos)
 * CREATE TABLE users (
 *      name VARCHAR(20) NOT NULL CHECK LEN(name) >= 3 
 * )
 */


import org.hibernate.validator.constraints.Length;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewTicketDTO(

        @NotNull(message = "O dono é obrigatório")
        Integer owner_id,

        Integer recipient_id,
        
        @NotNull(message = "O objeto é obrigatório")
        @NotBlank(message = "Não pode ser composta apenas de espaços")
        @Length(min = 3, message = "O objeto deve ter no mínimo 3 caracteres")
        String object,

        @NotNull(message = "A ação é obrigatório")
        @NotBlank(message = "Não pode ser composta apenas de espaços")
        @Length(min = 3, message = "A ação deve ter no mínimo 3") 
        String action,

        @NotNull(message = "O detalhe é obrigatório")
        @NotBlank(message = "Não pode ser composta apenas de espaços")
        @Length(min = 3, message = "Os detalhes devem ter no mínimo 3")
        String details,

        @NotNull(message = "O local é obrigatório")
        @NotBlank(message = "Não pode ser composta apenas de espaços")
        @Length(min = 3, message = "O local deve ter no mínimo 3")
        String local
        
        
)  {

}
