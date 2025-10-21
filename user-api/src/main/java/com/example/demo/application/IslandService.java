package com.example.demo.application;

import java.util.HashSet;
import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.demo.controller.dto.NewIslandDTO;
import com.example.demo.controller.dto.NewWorkstationDTO;
import com.example.demo.domain.exceptions.NotFoundException;
import com.example.demo.repository.IslandRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Island;

import jakarta.validation.Valid;

@Service
@Validated
public class IslandService {
    private IslandRepository islandRepository;

    private UserRepository userRepository;

    public IslandService(
        IslandRepository islandRepository,
        UserRepository userRepository
    ) {
        this.islandRepository = islandRepository;
        this.userRepository=userRepository;
    }

    public Island saveIsland(@Valid NewIslandDTO newIsland) {
        String description = newIsland.description();
        Island.Disposition disposition = newIsland.disposition();
        Set<NewWorkstationDTO> workstationDTOs = new HashSet<>(newIsland.workstations());
        return islandRepository.save(
            new Island(
                description,
                disposition,
                workstationDTOs
            )
        );
    }

    public void allocateAvailableWorkstation(@NonNull Integer userId) {

        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException());

        final var islands = islandRepository.findIslandWithAvailableWorkstations();

        if (islands.isEmpty()) {
            throw new IllegalStateException("Workstations not available");
        }

        Island freeIsland = Island.selectIslandWithAvailableWorkstations(islands)
            .orElseThrow(()->new NotFoundException())
        ;

        freeIsland.assignUserToTheFirstWorkstationAvailable(user);

        islandRepository.save(freeIsland);

    }

    public Island findIslandById(@NonNull Integer islandId) {
        return islandRepository.findById(islandId.longValue())
                .orElseThrow(() -> new NotFoundException());
    }

}
