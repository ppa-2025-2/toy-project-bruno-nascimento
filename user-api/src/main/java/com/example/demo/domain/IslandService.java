package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.demo.controller.dto.NewIslandDTO;
import com.example.demo.domain.exceptions.NotFoundException;
import com.example.demo.repository.IslandRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Island;
import com.example.demo.repository.entity.Workstation;

import jakarta.validation.Valid;


@Service
@Validated
public class IslandService {
    private final IslandRepository islandRepository;

    private final UserRepository userRepository;

    public IslandService(
        IslandRepository islandRepository,
        UserRepository userRepository
    ) {
        this.islandRepository = islandRepository;
        this.userRepository = userRepository;
    }

    public Island saveIsland(@Valid NewIslandDTO newIsland) {
        LocalDateTime now = LocalDateTime.now();
        Island island = new Island();

        Set<Workstation> workstations = new HashSet<>();
        newIsland.workstations().forEach(wsDto -> {
            Workstation ws = new Workstation();
            ws.setId(UUID.randomUUID().toString());
            ws.setSpecs(wsDto.specs());
            ws.setIsland(island);
            ws.setCreatedAt(now);
            ws.setUpdatedAt(now);
            workstations.add(ws);
        });

        island.setDescription(newIsland.description());
        island.setDisposition(newIsland.disposition());
        island.setWorkstations(workstations);
        island.setUpdatedAt(now);
        island.setCreatedAt(now);

        islandRepository.save(island);

        return island;
    }

    public void allocateAvailableWorkstation(@NonNull Integer userId) {

        final var user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException());

        final var islands = islandRepository.findIslandWithAvailableWorkstations();

        if (islands.isEmpty()) {
            throw new IllegalStateException("Workstations not available");
        }

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

        freeIsland.getWorkstations().stream()
            .filter(ws -> ws.getUser() == null)
            .findFirst()
            .ifPresent(ws -> ws.setUser(user));

        islandRepository.save(freeIsland);
    }

    public Island findIslandById(@NonNull Integer islandId) {
        return islandRepository.findById(islandId.longValue())
            .orElseThrow(() -> new NotFoundException());
    }
}
