package org.example.GraphQL;


import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.example.Models.Plants;
import org.example.Services.PlantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlantQuery implements GraphQLQueryResolver {

    @Autowired
    private PlantsService service;

    public List<Plants> getPlants() {
        return service.findAll();
    }

    public Plants getPlant(String name) {
        return service.findByName(name);
    }
}
