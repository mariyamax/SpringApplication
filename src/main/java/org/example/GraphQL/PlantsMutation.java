package org.example.GraphQL;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Services.PlantsService;
import org.example.Services.UsersService;
import org.example.Utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PlantsMutation implements GraphQLMutationResolver {

    @Autowired
    private PlantsService service;

    @Autowired
    private UsersService usersService;

    public Plants createPlant(String name, String family, String area, int coast, String description, String token) {
        Users user = usersService.findByToken(token);
        Plants resource = new Plants();
        resource.setName(name);
        resource.setFamily(family);
        resource.setArea(area);
        resource.setCoast(coast);
        resource.setDescription(description);
        resource.setUserToken(token);
        if (user == null || resource.getCoast() < 0 || service.findByName(resource.getName())!=null) {
            return null;
        }
        service.save(resource);
        return service.findByName(resource.getName());
    }

    public Plants updatePlant(String name, String family, String area, int coast, String description, String token) {
        Users user = usersService.findByToken(token);
        Plants resource = new Plants();
        resource.setName(name);
        resource.setFamily(family);
        resource.setArea(area);
        resource.setCoast(coast);
        resource.setDescription(description);
        resource.setUserToken(token);
        if (user == null || resource.getCoast() < 0
                || service.findByName(resource.getName())==null
                || !service.findByName(resource.getName()).getUserToken().equals(token)) {
            return null;
        }
        Plants currentPlant = service.findByName(resource.getName());
        resource = (Plants) PersistenceUtils.partialUpdate(currentPlant, resource);
        service.save(resource);
        return service.findByName(resource.getName());
    }

    public Boolean buyPlant(String name, String token) {
        Plants plant = service.findByName(name);
        Users buyer = usersService.findByToken(token);
        if (buyer != null && plant != null) {
            Users owner = usersService.findByToken(plant.getUserToken());
            return service.sell(buyer, owner, plant);
        }
        return false;
    }

    public Boolean deletePlant(String name, String token){
        Plants plants = service.findByName(name);
        if(plants == null || plants.getUserToken()!= token) {
            return false;
        }
        service.deleteById(plants.getSid());
        return true;
    }

}
