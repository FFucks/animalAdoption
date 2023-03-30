package com.api.animaladoption.repository;

import com.api.animaladoption.models.AnimalAdoptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalAdoptionRepository extends JpaRepository<AnimalAdoptionModel, Long> {

    AnimalAdoptionModel findByNameOrDescription(String name, String description);


    @Query("SELECT a " +
            "FROM AnimalAdoptionModel a " +
            "WHERE ((:term IS NULL) " +
            "OR (lower(a.name) LIKE lower(concat('%', :term, '%')) OR lower(a.description) LIKE lower(concat('%', :term, '%')))) " +
            "AND (:category IS NULL OR a.category = :category) " +
            "AND (:status IS NULL OR a.status = :status) " +
            "AND (:creationDate IS NULL OR a.creationDate = :creationDate)")
    List<AnimalAdoptionModel> findByNameOrDescriptionAndCategoryAndStatusAndCreationDate(String term, String category, String status, String creationDate);

    @Modifying
    @Query("UPDATE AnimalAdoptionModel a SET a.status = :status WHERE (:id IS NULL OR a.id = :id) AND (:name IS NULL OR a.name = :name) AND (:alias IS NULL OR a.alias = :alias)")
    int updateAnimalStatus(@Param("id") Long id, @Param("name") String name, @Param("alias") String alias, @Param("status") String status);

}
