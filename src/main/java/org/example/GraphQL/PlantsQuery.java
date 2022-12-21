package org.example.GraphQL;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.example.Models.Plants;
import org.example.Services.PlantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class PlantsQuery implements GraphQLQueryResolver {

    @Autowired
    private PlantsService service;

    public List<Plants> getPlants() {
        return service.findAll();
    }

    public Plants getPlant(Long id) {
        return service.findById(id);
    }

}
