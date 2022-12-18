package org.example.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.example.Models.Plants;
import org.example.Services.PlantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PlantQuery implements GraphQLQueryResolver {

    @Autowired
    private PlantsService service;

    public List<Plants> getPlants() {
        return service.findAll();
    }

    public Plants getPlant(Long id) {
        return service.findById(id);
    }

}
