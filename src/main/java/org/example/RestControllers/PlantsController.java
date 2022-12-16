package org.example.RestControllers;

import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Services.PlantsService;
import org.example.Services.UsersService;
import org.example.Utils.CustomTokenUtils;
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

    @GetMapping
    public List<Plants> findAll(String token) {
        List<Plants> plants = service.findAll();
        Users principal = usersService.findByName(CustomTokenUtils.encodeToUsername(token));
        if (principal!=null) {
            return plants;
        } else {
            return null;
        }
    }

    @GetMapping(value = "/{id}")
    public Plants findById(@PathVariable("id") Long id, String token) {
        Users principal = usersService.findByName(CustomTokenUtils.encodeToUsername(token));
        if (principal!=null) {
            return service.findById(id);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(Plants resource, @RequestParam(name = "file", required = false) MultipartFile file, String token) {
        Users principal = usersService.findByName(CustomTokenUtils.encodeToUsername(token));
        if (principal != null) {
            service.save(resource, file, principal);
            return service.findByName(resource.getName()).getID();
        } else {
            return -1L;
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Boolean sell(@RequestParam(name = "name", required = false) String name, String token) {
        Plants plant = service.findByName(name);
        Users buyer = usersService.findByName(CustomTokenUtils.encodeToUsername(token));
        Users owner = usersService.findById(plant.getUserId());
        if (plant != null && buyer != null) {
            return service.sellPlant(buyer, owner, plant);
        } else {
            return false;
        }
    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Long update(Plants resource, @RequestParam(name = "file", required = false) MultipartFile file, String token) {
        Users principal = usersService.findByName(CustomTokenUtils.encodeToUsername(token));
        if (resource != null && principal!=null&&resource.getAuthor().equals(principal.getUsername())) {
            if (!file.isEmpty()) {
                service.addImageToPlant(resource, file);
            }
            Plants currentPlant = service.findByName(resource.getName());
            resource = (Plants) PersistenceUtils.partialUpdate(currentPlant, resource);
            service.save(resource);
            return service.findByName(resource.getName()).getID();
        } else {
            return -1L;
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long delete(@PathVariable("id") Long id, String token) {
        Users principal = usersService.findByName(CustomTokenUtils.encodeToUsername(token));
        Plants plants = service.findById(id);
        if (plants != null && principal!=null&&plants.getAuthor().equals(principal.getUsername())) {
            service.deleteById(id);
            return 1L;
        } else {
            return -1L;
        }
    }

}