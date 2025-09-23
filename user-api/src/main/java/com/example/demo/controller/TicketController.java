package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.controller.dto.NewTicketDTO;
import com.example.demo.controller.dto.PatchTicketDTO;
import com.example.demo.domain.TicketBusiness;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Ticket;


@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private TicketBusiness ticketBusiness;
    private TicketRepository ticketRepository;
    private UserRepository userRepository;

    public TicketController(
            TicketBusiness ticketBusiness,
            TicketRepository ticketRepository,
            UserRepository userRepository

        ) {
        this.ticketBusiness = ticketBusiness;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void newTicket(@RequestBody NewTicketDTO newTicket) {
        ticketBusiness.createTicket(newTicket);
    }

    
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void patchTicket(@RequestBody PatchTicketDTO patchTicket) {
        ticketBusiness.patchTicket(patchTicket);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ticket>> getTickets() {
        return ResponseEntity.ok(ticketRepository.findAll());
    }
}
