package com.example.demo.domain;

import java.util.HashSet;

import org.springframework.validation.annotation.Validated;

import com.example.demo.controller.dto.NewTicketDTO;
import com.example.demo.controller.dto.PatchTicketDTO;
import com.example.demo.domain.stereotype.Business;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Ticket;
import com.example.demo.repository.entity.User;

import jakarta.validation.Valid;


@Business
@Validated
public class TicketBusiness {

    private TicketRepository ticketRepository;

    private UserRepository userRepository;

    public TicketBusiness(
        TicketRepository ticketRepository,
        UserRepository userRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public void createTicket(@Valid NewTicketDTO newTicket){
        var ticket = new Ticket();
        var observers=new HashSet<User>();
        var owner = userRepository.findById(newTicket.owner_id());
        if(owner.isEmpty()){
            throw new IllegalArgumentException("Owner não encontrado");
        }
        ticket.setOwner(owner.get());
        observers.add(owner.get());
        if(newTicket.recipient_id() != null){
            var recipient = userRepository.findById(newTicket.recipient_id());
            if(recipient.isEmpty()) {
                throw new IllegalArgumentException("Recipient não encontrado");
            }
            ticket.setRecipient(recipient.get());
            observers.add(recipient.get());
        } else{
            ticket.setRecipient(owner.get());
        }
        ticket.setAction(newTicket.action());
        ticket.setDetails(newTicket.details());
        ticket.setObject(newTicket.object());
        ticket.setLocal(newTicket.local());
        ticket.setStatus(Ticket.Status.todo);
        ticketRepository.save(ticket);
    }

    public void patchTicket(@Valid PatchTicketDTO patchTicket){
        
    }
}
