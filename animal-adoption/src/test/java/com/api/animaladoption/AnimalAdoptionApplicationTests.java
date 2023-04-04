package com.api.animaladoption;

import com.api.animaladoption.dtos.AnimalDto;
import com.api.animaladoption.dtos.UpdateAnimalDto;
import com.api.animaladoption.models.AnimalAdoptionModel;
import com.api.animaladoption.models.Breed;
import com.api.animaladoption.models.Image;
import com.api.animaladoption.repository.AnimalAdoptionRepository;
import com.api.animaladoption.services.AnimalAdoptionService;
import com.api.animaladoption.services.AnimalApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AnimalAdoptionApplicationTests {

	private static final String TERMO = "Chow Chow";

	@Autowired
	AnimalAdoptionService animalAdoptionService;

	@MockBean
	AnimalAdoptionRepository animalAdoptionRepository;

	@MockBean
	AnimalApiService animalApiService;

	@BeforeEach
	public void init() {
		AnimalAdoptionModel model = new AnimalAdoptionModel();

		model.setId(1L);
		model.setName("Chow Chow");
		model.setAlias("Juvenal");
		model.setDescription("Brincalhão");
		model.setCategory("Cachorro");
		model.setStatus("Disponivel");
		model.setImage("https://cdn2.thedogapi.com/images/ry8KWgqEQ.jpg");
		model.setCreationDate("03-04-2023");

		List<AnimalAdoptionModel> modelList = new ArrayList<>();
		modelList.add(model);

		Mockito.when(animalAdoptionRepository.findByNameOrDescriptionAndCategoryAndStatusAndCreationDate(
				TERMO,
				model.getCategory(),
				model.getStatus(),
				model.getCreationDate()
		)).thenReturn(modelList);


	}

	@Test
	public void service_find_breeds_dog_OK() {

		AnimalDto animalDtoDog = new AnimalDto();
		animalDtoDog.setCategory("Cachorro");
		Breed breedDog1 = new Breed();
		breedDog1.setName("Chow Chow");
		breedDog1.setTemperament("Aloof, Loyal, Independent, Quiet");
		Breed breedDog2 = new Breed();
		breedDog2.setName("Golden Retriever");
		breedDog1.setTemperament("Intelligent, Kind, Reliable, Friendly, Trustworthy, Confident");

		List<Breed> expectedBreedsDogs = new ArrayList<>();
		expectedBreedsDogs.add(breedDog1);
		expectedBreedsDogs.add(breedDog2);

		Mockito.when(animalApiService.findAllbreeds("Cachorro")).thenReturn(expectedBreedsDogs);

		List<Breed> actualBreedsDog = animalAdoptionService.findBreeds(animalDtoDog);
		Assertions.assertEquals(expectedBreedsDogs, actualBreedsDog);
	}

	@Test
	public void service_find_breeds_cat_OK() {

		AnimalDto animalDtoCat = new AnimalDto();
		animalDtoCat.setCategory("Gato");
		Breed breedCat1 = new Breed();
		breedCat1.setName("Abyssinian");
		breedCat1.setTemperament("Active, Energetic, Independent, Intelligent, Gentle");
		Breed breedCat2 = new Breed();
		breedCat2.setName("Aegean");
		breedCat1.setTemperament("Affectionate, Social, Intelligent, Playful, Active");

		List<Breed> expectedBreedsCats = new ArrayList<>();
		expectedBreedsCats.add(breedCat1);
		expectedBreedsCats.add(breedCat2);

		Mockito.when(animalApiService.findAllbreeds("Gato")).thenReturn(expectedBreedsCats);

		List<Breed> actualBreedsCat = animalAdoptionService.findBreeds(animalDtoCat);
		Assertions.assertEquals(expectedBreedsCats, actualBreedsCat);
	}

	@Test
	public void test_converter_OK() {

		AnimalDto animalDto = new AnimalDto();
		animalDto.setName("Chow Chow");
		animalDto.setAlias("Juvenal");
		animalDto.setDescription("Brincalhão");
		animalDto.setStatus("Disponivel");
		animalDto.setCategory("Cachorro");

		Image dogImage = new Image();
		dogImage.setUrl("https://cdn2.thedogapi.com/images/ry8KWgqEQ.jpg");
		Breed breedDog1 = new Breed();
		breedDog1.setName("Chow Chow");
		breedDog1.setImage(dogImage);

		List<Breed> breeds = new ArrayList<>();
		breeds.add(breedDog1);

		AnimalAdoptionModel expectedModel = new AnimalAdoptionModel();
		expectedModel.setName("Chow Chow");
		expectedModel.setAlias("Juvenal");
		expectedModel.setDescription("Brincalhão");
		expectedModel.setStatus("Disponivel");
		expectedModel.setCategory("Cachorro");
		expectedModel.setImage("https://cdn2.thedogapi.com/images/ry8KWgqEQ.jpg");
		expectedModel.setCreationDate(Comons.getUtcNow());

		Mockito.when(animalAdoptionService.findBreeds(animalDto)).thenReturn(breeds);

		AnimalAdoptionModel actualModel = animalAdoptionService.converter(animalDto);

		Assertions.assertEquals(expectedModel.getName(), actualModel.getName());
		Assertions.assertEquals(expectedModel.getAlias(), actualModel.getAlias());
		Assertions.assertEquals(expectedModel.getDescription(), actualModel.getDescription());
		Assertions.assertEquals(expectedModel.getStatus(), actualModel.getStatus());
		Assertions.assertEquals(expectedModel.getCategory(), actualModel.getCategory());
		Assertions.assertEquals(expectedModel.getImage(), actualModel.getImage());
		Assertions.assertNotNull(actualModel.getCreationDate());
	}

	@Test
	public void test_find_all_animals_OK() {

		AnimalAdoptionModel animalDog = new AnimalAdoptionModel();
		animalDog.setId(1L);
		animalDog.setName("Chow Chow");
		animalDog.setAlias("Juvenal");
		animalDog.setDescription("Brincalhão");
		animalDog.setStatus("Disponivel");
		animalDog.setImage("https://cdn2.thedogapi.com/images/ry8KWgqEQ.jpg");
		animalDog.setCreationDate("03-04-2023");

		List<AnimalAdoptionModel> animalList = List.of(animalDog);
		Mockito.when(animalAdoptionRepository.findAll()).thenReturn(animalList);

		List<AnimalAdoptionModel> actualAnimals = animalAdoptionRepository.findAll();
		Assertions.assertEquals(animalList.size(), actualAnimals.size());
		Assertions.assertEquals(animalList.get(0).getName(), actualAnimals.get(0).getName());
		Assertions.assertEquals(animalList.get(0).getAlias(), actualAnimals.get(0).getAlias());
		Assertions.assertEquals(animalList.get(0).getDescription(), actualAnimals.get(0).getDescription());
		Assertions.assertEquals(animalList.get(0).getStatus(), actualAnimals.get(0).getStatus());
		Assertions.assertEquals(animalList.get(0).getCategory(), actualAnimals.get(0).getCategory());
	}

	@Test
	public void test_save_animal_adoption_OK() {

		AnimalAdoptionModel animalDog = new AnimalAdoptionModel();
		animalDog.setId(1L);
		animalDog.setName("Chow Chow");
		animalDog.setAlias("Juvenal");
		animalDog.setDescription("Brincalhão");
		animalDog.setStatus("Disponivel");
		animalDog.setImage("https://cdn2.thedogapi.com/images/ry8KWgqEQ.jpg");
		animalDog.setCreationDate("03-04-2023");

		Mockito.when(animalAdoptionRepository.save(animalDog)).thenReturn(animalDog);

		AnimalAdoptionModel actualAnimal = animalAdoptionService.saveAnimalAdoption(animalDog);

		Assertions.assertEquals(animalDog.getName(), actualAnimal.getName());
		Assertions.assertEquals(animalDog.getAlias(), actualAnimal.getAlias());
		Assertions.assertEquals(animalDog.getDescription(), actualAnimal.getDescription());
		Assertions.assertEquals(animalDog.getStatus(), actualAnimal.getStatus());
		Assertions.assertEquals(animalDog.getCategory(), actualAnimal.getCategory());
	}

	@Test
	public void test_get_animal_OK() {

		AnimalAdoptionModel animalDog = new AnimalAdoptionModel();
		animalDog.setId(1L);
		animalDog.setName("Chow Chow");
		animalDog.setAlias("Juvenal");
		animalDog.setDescription("Brincalhão");
		animalDog.setStatus("Disponivel");
		animalDog.setImage("https://cdn2.thedogapi.com/images/ry8KWgqEQ.jpg");
		animalDog.setCreationDate("03-04-2023");

		List<AnimalAdoptionModel> expectedAnimals = List.of(animalDog);

		String term = "Brincalhão";
		String category = "Cachorro";
		String status = "Disponivel";
		String creationDate = "03-04-2023";

		Mockito.when(animalAdoptionRepository.findByNameOrDescriptionAndCategoryAndStatusAndCreationDate(term, category, status, creationDate)).thenReturn(expectedAnimals);

		List<AnimalAdoptionModel> actualAnimals = animalAdoptionService.getAnimal(term, category, status, creationDate);

		Assertions.assertEquals(expectedAnimals.size(), actualAnimals.size());
	}

	@Test
	public void test_update_animal_status_OK() {
		int expectedRowsUpdated = 1;

		UpdateAnimalDto updateAnimalDto = new UpdateAnimalDto();
		updateAnimalDto.setId(1L);
		updateAnimalDto.setName("Chow Chow");
		updateAnimalDto.setAlias("Juvenal");
		updateAnimalDto.setStatus("Disponivel");


		Mockito.when(animalAdoptionRepository.updateAnimalStatus(updateAnimalDto.getId(),
																 updateAnimalDto.getName(),
																 updateAnimalDto.getAlias(),
																 updateAnimalDto.getStatus())).thenReturn(expectedRowsUpdated);

		int actualRowsUpdated = animalAdoptionService.updateAnimalStatus(updateAnimalDto);

		Assertions.assertEquals(expectedRowsUpdated, actualRowsUpdated);

		Mockito.verify(animalAdoptionRepository, Mockito.times(1))
				.updateAnimalStatus(updateAnimalDto.getId(), updateAnimalDto.getName(), updateAnimalDto.getAlias(), updateAnimalDto.getStatus());
	}

}
