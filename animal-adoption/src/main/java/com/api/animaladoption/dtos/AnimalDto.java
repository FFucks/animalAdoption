package com.api.animaladoption.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class AnimalDto {

    @NotBlank
    @JsonProperty("nome")
    private String name;
    @NotBlank
    @JsonProperty("apelido")
    private String alias;
    @NotBlank
    @JsonProperty("descricao")
    private String description;
    @NotBlank
    @JsonProperty("categoria")
    private String category;
    @NotBlank
    private String status;

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
}
