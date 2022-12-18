package org.example.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Services.PlantsService;
import org.example.Services.UsersService;
import org.example.Utils.CustomTokenUtils;
import org.example.Utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlantMutation implements GraphQLMutationResolver {

    @Autowired
    private PlantsService service;

    @Autowired
    private UsersService usersService;

    public Plants createPlant(String name, String family, String area, int coast, String description, String token) {
        Users user = usersService.findByName(CustomTokenUtils.encodeToUsername(token));
        if (user != null && coast >= 0) {
            Plants plant = new Plants();
            plant.setName(name);
            plant.setFamily(family);
            plant.setArea(area);
            plant.setCoast(coast);
            plant.setDescription(description);
            plant.setUserId(user.getSid());
            plant.setAuthor(user.getUsername());
            service.save(plant);
            return service.findByName(name);
        }
        return new Plants();
    }

    public Plants updatePlant(String name, String family, String area, int coast, String description, String token) {
        Users user = usersService.findByName(CustomTokenUtils.encodeToUsername(token));
        Plants resource = new Plants();
        resource.setName(name);
        resource.setFamily(family);
        resource.setArea(area);
        resource.setCoast(coast);
        resource.setDescription(description);
        resource.setUserId(user.getSid());
        resource.setAuthor(user.getUsername());
        Plants currentPlant = service.findByName(name);
        if (currentPlant != null && user != null && currentPlant.getUserId()==user.getSid()) {
            resource = (Plants) PersistenceUtils.partialUpdate(currentPlant, resource);
            service.save(resource);
            return service.findByName(resource.getName());
        } else {
            return null;
        }
    }

    public Plants buyPlant(String name, String token) {
        Plants plant = service.findByName(name);
        Users buyer = usersService.findByName(CustomTokenUtils.encodeToUsername(token));
        if (plant != null && buyer != null) {
            Users owner = usersService.findById(plant.getUserId());
            if (service.sellPlant(buyer, owner, plant)) {
                return service.findByName(name);
            }
        } else {
            return null;
        }
        return null;
    }



}
