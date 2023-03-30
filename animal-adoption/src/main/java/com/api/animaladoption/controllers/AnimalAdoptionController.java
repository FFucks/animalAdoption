package com.api.animaladoption.controllers;

import com.api.animaladoption.dtos.AnimalDto;
import com.api.animaladoption.dtos.UpdateAnimalDto;
import com.api.animaladoption.models.AnimalAdoptionModel;
import com.api.animaladoption.models.Breed;
import com.api.animaladoption.services.AnimalAdoptionService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
public class AnimalAdoptionController {

    private final AnimalAdoptionService animalAdoptionService;

    @Autowired
    public AnimalAdoptionController(AnimalAdoptionService animalAdoptionService) {
        this.animalAdoptionService = animalAdoptionService;
    }

    @PostMapping("/animal")
    public ResponseEntity<Object> addAnimal(@RequestBody @Valid AnimalDto animalDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalAdoptionService.saveAnimalAdoption(animalAdoptionService.converter(animalDto)));
    }

    @GetMapping("/animals")
    public ResponseEntity<List<AnimalAdoptionModel>> getAnimals() {
        return ResponseEntity.status(HttpStatus.OK).body(animalAdoptionService.findAllAnimals());
    }

    @GetMapping("/animal")
    public ResponseEntity<List<AnimalAdoptionModel>> getAnimal(@PathParam("termo") String termo,
                                                               @PathParam("categoria") String categoria,
                                                               @PathParam("status") String status,
                                                               @PathParam("dataCriacao") String dataCriacao) {
        return ResponseEntity.status(HttpStatus.OK).body(animalAdoptionService.getAnimal(termo, categoria, status, dataCriacao));
    }

    @PatchMapping("/animal")
    public ResponseEntity<Integer> patchAnimal(@RequestBody @Valid UpdateAnimalDto updateAnimalDto) {
        return ResponseEntity.status(HttpStatus.OK).body(animalAdoptionService.updateAnimalStatus(updateAnimalDto));
    }
}
