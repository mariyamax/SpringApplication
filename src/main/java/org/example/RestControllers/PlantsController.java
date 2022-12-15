package org.example.RestControllers;

import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Services.PlantsService;
import org.example.Services.UsersService;
import org.example.Utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class PlantsController {

    @Autowired
    private PlantsService service;
    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<Plants> findAll() {
        List<Plants> plants = service.findAll();
        return plants;
    }

    @GetMapping(value = "/{id}")
    public Plants findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(Plants resource, @RequestParam(name = "file", required = false) MultipartFile file, Principal principal, HttpServletResponse response) {
        try {
            response.sendRedirect("/plants");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        service.save(resource, file, principal);
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public void changeAuthor(@RequestParam(name = "name", required = false) String name, Principal principal, HttpServletResponse response) {
        Plants plant = service.findByName(name);
        Users buyer = usersService.findByName(principal.getName());
        Users owner = usersService.findById(plant.getUserId());
        service.sellPlant(buyer, owner, plant);
        try {
            response.sendRedirect("/plants");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(Plants resource, @RequestParam(name = "file", required = false) MultipartFile file, HttpServletResponse response) {
        try {
            response.sendRedirect("/plants");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!file.isEmpty()) {
            service.addImageToPlant(resource, file);
        }
        Plants currentPlant = service.findByName(resource.getName());
        resource = (Plants) PersistenceUtils.partialUpdate(currentPlant, resource);
        service.save(resource);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            response.sendRedirect("/plants");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        service.deleteById(id);
    }

}