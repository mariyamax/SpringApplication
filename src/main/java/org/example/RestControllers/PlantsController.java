package org.example.RestControllers;

import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Services.CustomTokenService;
import org.example.Services.PlantsService;
import org.example.Services.UsersService;
import org.example.Utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class PlantsController {

    @Autowired
    private PlantsService service;
    @Autowired
    private UsersService usersService;
    @Autowired
    private CustomTokenService tokenService;

    @GetMapping
    public List<Plants> findAll(String token) {
        List<Plants> plants = service.findAll();
        Users principal = usersService.findByUsername(tokenService.decodeToUsername(token));
        if (principal!=null) {
            return plants;
        } else {
            return null;
        }
    }

    @GetMapping(value = "/{id}")
    public Plants findById(@PathVariable("id") Long id, String token) {
        Users principal = usersService.findByUsername(tokenService.decodeToUsername(token));
        if (principal!=null) {
            Plants plants = service.findById(id);
            return plants;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(Plants resource, @RequestParam(name = "file", required = false) MultipartFile file, String token) {
        Users user = usersService.findByUsername(tokenService.decodeToUsername(token));
        if (user == null || resource.getCoast() < 0 || service.findByName(resource.getName())!=null) {
            return -1L;
        }
        service.create(resource, file, user);
        return service.findByName(resource.getName()).getSid();
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Boolean sell(@RequestParam(name = "name", required = false) String name, String token) {
        Plants plant = service.findByName(name);
        Users buyer = usersService.findByUsername(tokenService.decodeToUsername(token));
        if (plant != null && buyer != null) {
            Users owner = usersService.findById(plant.getUserId());
            return service.sell(buyer, owner, plant);
        } else {
            return false;
        }
    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Long update(Plants resource, @RequestParam(name = "file", required = false) MultipartFile file, String token) {
        Users user = usersService.findByUsername(tokenService.decodeToUsername(token));
        if (user == null || resource.getCoast() < 0
                || service.findByName(resource.getName())==null
                || service.findByName(resource.getName()).getUserId()!=user.getSid()) {
            return -1L;
        }
        if (!file.isEmpty()) {
            service.addImage(resource, file);
        }
        Plants currentPlant = service.findByName(resource.getName());
        resource = (Plants) PersistenceUtils.partialUpdate(currentPlant, resource);
        service.save(resource);
        return service.findByName(resource.getName()).getSid();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean delete(@PathVariable("id") Long id, String token) {
        Users user = usersService.findByUsername(tokenService.decodeToUsername(token));
        if (user == null || service.findById(id)==null || service.findById(id).getUserId()!=user.getSid()) {
            return false;
        }
        service.deleteById(id);
        return true;
    }

}