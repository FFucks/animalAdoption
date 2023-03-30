package com.api.animaladoption.services;

import com.api.animaladoption.Comons;
import com.api.animaladoption.dtos.AnimalDto;
import com.api.animaladoption.dtos.UpdateAnimalDto;
import com.api.animaladoption.models.AnimalAdoptionModel;
import com.api.animaladoption.models.Breed;
import com.api.animaladoption.repository.AnimalAdoptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalAdoptionService {
    private final static String CACHORRO = "Cachorro";
    private final static String GATO = "Gato";

    private final AnimalAdoptionRepository animalAdoptionRepository;
    private final AnimalApiService animalApiService;

    @Autowired
    public AnimalAdoptionService(AnimalAdoptionRepository animalAdoptionRepository,
                                 AnimalApiService animalApiService) {
        this.animalAdoptionRepository = animalAdoptionRepository;
        this.animalApiService = animalApiService;
    }

    public List<Breed> findBreeds(AnimalDto animalDto) {
        List<Breed> breeds = null;

        if (CACHORRO.equalsIgnoreCase(animalDto.getCategory())) {
            breeds = animalApiService.findAllbreeds(CACHORRO);
        } else {
            breeds = animalApiService.findAllbreeds(GATO);
        }

        return breeds;
    }

    public AnimalAdoptionModel converter(AnimalDto animalDto) {

        List<Breed> breeds = this.findBreeds(animalDto);

        AnimalAdoptionModel animalAdoptionModel = new AnimalAdoptionModel();

        animalAdoptionModel.setName(animalDto.getName());
        animalAdoptionModel.setAlias(animalDto.getAlias());
        animalAdoptionModel.setDescription(animalDto.getDescription());
        animalAdoptionModel.setStatus(animalDto.getStatus());
        animalAdoptionModel.setCreationDate(Comons.getUtcNow());

        Optional<Breed> firstBreed = breeds.stream().filter(b -> b.getName().equalsIgnoreCase(animalDto.getName())).findFirst();

        if (!breeds.isEmpty() && firstBreed.isPresent()) {
            animalAdoptionModel.setCategory(animalDto.getCategory());
            animalAdoptionModel.setImage(firstBreed.get().getImage().getUrl());
        } else {
            animalAdoptionModel.setCategory(animalDto.getCategory());
            animalAdoptionModel.setImage(null);
        }

        return animalAdoptionModel;
    }

    public List<AnimalAdoptionModel> findAllAnimals() {
        return animalAdoptionRepository.findAll();
    }

    @Transactional
    public AnimalAdoptionModel saveAnimalAdoption(AnimalAdoptionModel animalAdoptionModel) {
        return animalAdoptionRepository.save(animalAdoptionModel);
    }
    public List<AnimalAdoptionModel> getAnimal(String term, String category, String status, String creationDate) {
        return animalAdoptionRepository.findByNameOrDescriptionAndCategoryAndStatusAndCreationDate(term, category, status, creationDate);
    }

    @Transactional
    public int updateAnimalStatus(UpdateAnimalDto updateAnimalDto) {
        return animalAdoptionRepository.updateAnimalStatus(updateAnimalDto.getId(),
                                                           updateAnimalDto.getName(),
                                                           updateAnimalDto.getAlias(),
                                                           updateAnimalDto.getStatus());
    }
}
