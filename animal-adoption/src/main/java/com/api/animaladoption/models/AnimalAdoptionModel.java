package com.api.animaladoption.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class AnimalAdoptionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @JsonProperty("nome")
    private String name;

    @Column(name = "alias")
    @JsonProperty("apelido")
    private String alias;

    @Column(name = "description")
    @JsonProperty("descricao")
    private String description;

    @Column(name = "category")
    @JsonProperty("categoria")
    private String category;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "image")
    @JsonProperty("imagem")
    private String image;

    @Column(name = "creationDate")
    @JsonProperty("dataCriacao")
    private String creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
