package com.api.animaladoption.services;

import com.api.animaladoption.models.Breed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalApiService {
    @Value("${client.dog.api_key}")
    private String dogApiKey;
    @Value("${client.dog.url}")
    private String dogUrl;
    @Value("${client.cat.api_key}")
    private String catApiKey;
    @Value("${client.cat.url}")
    private String catUrl;
    private final RestTemplate restTemplate;
    @Autowired
    public AnimalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Breed> findAllbreeds(String category) {
        List<Breed> breeds = new ArrayList<Breed>();
        StringBuilder url = new StringBuilder(category.equalsIgnoreCase("Cachorro") ?
                dogUrl : catUrl);
        url.append("/breeds");
        url.append("?api_key");
        url.append(category.equalsIgnoreCase("Cachorro") ? dogApiKey : catApiKey);

        try {
            ResponseEntity<List<Breed>> responseEntity = restTemplate.exchange(
                    url.toString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            breeds = responseEntity.getBody();

        } catch (Exception e) {
            System.out.println(e);
        }
        return breeds;
    }
}
