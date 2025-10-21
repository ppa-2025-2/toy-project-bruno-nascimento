package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import com.example.demo.controller.dto.NewWorkstationDTO;
import com.example.demo.domain.stereotype.Business;
import com.example.demo.repository.entity.Island;
import com.example.demo.repository.entity.User;
import com.example.demo.repository.entity.Workstation;

import jakarta.validation.Valid;


@Business
@Validated
public class IslandBusiness {

    public Island createIsland(
        @Valid String description,
        @Valid Island.Disposition disposition,
        @Valid Set<NewWorkstationDTO> workstationsDTO
    ) {
        LocalDateTime now = LocalDateTime.now();
        Island island = new Island();
        island.setDescription(description);
        island.setDisposition(disposition);
        island.setCreatedAt(now);
        island.setUpdatedAt(now);

        Set<Workstation> workstations = new java.util.HashSet<>();
        for (NewWorkstationDTO dto : workstationsDTO) {
            Workstation workstation = new Workstation();
            workstation.setId(UUID.randomUUID().toString());
            workstation.setSpecs(dto.specs());
            workstation.setIsland(island);
            workstation.setCreatedAt(now);
            workstation.setUpdatedAt(now);
            workstations.add(workstation);
        }

        island.setWorkstations(workstations);
        return island;
    }

    public void allocateUserToAvailableWorkstation(
        @NonNull User user,
        @NonNull Island island
    ) {
        island.getWorkstations().stream()
            .filter(ws->ws.getUser() == null)
            .findFirst()
            .ifPresent(ws -> {
                ws.setUser(user);
                ws.setUpdatedAt(LocalDateTime.now());
            });
    }

    public Optional<Island> selectIslandWithAvailableWorkstations(List<Island> islands){
        Island freeIsland = islands.getFirst();
        for (int slots = 1; slots < Island.Disposition.CIRCULAR.getPlacements(); slots++) {
            final int positions = slots;
            var possibleIsland = islands.stream()
                .filter(i -> i.getWorkstations().stream()
                            .map(Workstation::getUser)
                            .filter(Objects::nonNull)
                            .count() == positions)
                .findFirst();
            if (possibleIsland.isPresent()) {
                freeIsland = possibleIsland.get();
                break;
            }
        }
        return Optional.ofNullable(freeIsland);
    }

}
