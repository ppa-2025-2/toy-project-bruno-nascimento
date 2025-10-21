package com.example.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.AllocateWorkstationDTO;
import com.example.demo.controller.dto.NewIslandDTO;
import com.example.demo.domain.IslandService;
import com.example.demo.repository.entity.Island;


@RestController
@RequestMapping("/api/v1/islands")
public class IslandController {
    private IslandService islandService;

    public IslandController(
        IslandService islandService
    ) {
        this.islandService = islandService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Island newIsland(@RequestBody NewIslandDTO newIsland) {
        return islandService.saveIsland(newIsland);
    }

    @PatchMapping(path = "/workstations/allocate-available",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public void allocateWorkstation(@RequestBody AllocateWorkstationDTO allocateWorkstationDTO) {
        islandService.allocateAvailableWorkstation(allocateWorkstationDTO.userId());
    }

    @GetMapping(value = "/{islandId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Island getIsland(@PathVariable Integer islandId) {
        return islandService.findIslandById(islandId);
    }

}
