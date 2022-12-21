package org.example.GraphQL;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Services.CustomTokenService;
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

    @Autowired
    private CustomTokenService tokenService;

    public Plants createPlant(String name, String family, String area, int coast, String description, String token) {
        Users user = usersService.findByUsername(tokenService.decodeToUsername(token));
        if (user == null || coast < 0 || service.findByName(name)!=null) {
            return null;
        }
        Plants resource = new Plants();
        resource.setName(name);
        resource.setFamily(family);
        resource.setArea(area);
        resource.setCoast(coast);
        resource.setDescription(description);
        resource.setUserId(user.getSid());
        service.save(resource);
        return service.findByName(resource.getName());
    }

    public Plants updatePlant(String name, String family, String area, int coast, String description, String token) {
        Users user = usersService.findByUsername(tokenService.decodeToUsername(token));
        if (user == null || coast < 0
                || service.findByName(name)==null
                || service.findByName(name).getUserId()!=user.getSid()) {
            return null;
        }
        Plants resource = new Plants();
        resource.setName(name);
        resource.setFamily(family);
        resource.setArea(area);
        resource.setCoast(coast);
        resource.setDescription(description);
        resource.setUserId(user.getSid());
        Plants currentPlant = service.findByName(resource.getName());
        resource = (Plants) PersistenceUtils.partialUpdate(currentPlant, resource);
        service.save(resource);
        return service.findByName(resource.getName());
    }

    public Boolean buyPlant(String name, String token) {
        Plants plant = service.findByName(name);
        Users buyer = usersService.findByUsername(tokenService.decodeToUsername(token));
        if (buyer != null && plant != null) {
            Users owner = usersService.findById(plant.getUserId());
            return service.sell(buyer, owner, plant);
        }
        return false;
    }

    public Boolean deletePlant(String name, String token){
        Plants plants = service.findByName(name);
        Users user = usersService.findByUsername(tokenService.decodeToUsername(token));
        if(plants == null || plants.getUserId() != user.getSid()) {
            return false;
        }
        service.deleteById(plants.getSid());
        return true;
    }

}
