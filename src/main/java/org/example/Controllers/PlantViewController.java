package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Services.PlantsService;
import org.example.Services.UsersService;
import org.example.Utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
@RequestMapping("/plants")
public class PlantViewController {

    @Autowired
    private PlantsService plantService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/unique")
    public String plantSearching(@RequestParam String name, Model model) {
        Plants plant;
        try {
            plant = plantService.findByName(name);
        } catch (NullPointerException e) {
            return "plants";
        }
        model.addAttribute("plant", plant);
        return "plantProfile";
    }

    @GetMapping
    public String plants(Model model) {
        model.addAttribute("plants", plantService.findAll());
        return "plants";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Plants resource, Model model, @RequestParam(name = "file", required = false) MultipartFile file, String token) {
        Users user = usersService.findByToken(token);
        if (user == null) {
            model.addAttribute("creationError","invalid token");
            model.addAttribute("plant", new Plants());
            return "settings";
        }
        if (resource.getCoast() < 0) {
            model.addAttribute("creationError","invalid coast");
            model.addAttribute("plant", new Plants());
            return "settings";
        }
        if (plantService.findByName(resource.getName())!=null) {
            model.addAttribute("creationError","invalid name");
            model.addAttribute("plant", new Plants());
            return "settings";
        }
        plantService.create(resource, file, user);
        model.addAttribute("plants", plantService.findAll());
        return "plants";
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public String sell(@RequestParam String name, String token, Model model) {
        Plants plant = plantService.findByName(name);
        Users buyer = usersService.findByToken(token);
        if (buyer == null) {
            model.addAttribute("plant", plant);
            model.addAttribute("error","invalid token");
            return "plantProfile";
        }
        Users owner = usersService.findByToken(plant.getUserToken());
        if (!plantService.sell(buyer, owner, plant)){
            model.addAttribute("plant", plant);
            model.addAttribute("error","not enough coins");
            return "plantProfile";
        }
        model.addAttribute("plants", plantService.findAll());
        return "plants";
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String update(Plants resource, @RequestParam(name = "file", required = false) MultipartFile file, String token, Model model) {
        Users user = usersService.findByToken(token);
        Plants currentPlant = plantService.findByName(resource.getName());
        if (user == null) {
            model.addAttribute("updateError","invalid token");
            model.addAttribute("plant", new Plants());
            return "settings";
        }
        if (resource.getCoast() < 0) {
            model.addAttribute("updateError","invalid coast");
            model.addAttribute("plant", new Plants());
            return "settings";
        }
        if (currentPlant==null) {
            model.addAttribute("updateError","invalid name");
            model.addAttribute("plant", new Plants());
            return "settings";
        }
        if (!currentPlant.getUserToken().equals(token)) {
            model.addAttribute("updateError","invalid plant to update");
            model.addAttribute("plant", new Plants());
            return "settings";
        }
        if (!file.isEmpty()) {
            plantService.addImage(resource, file);
        }
        resource = (Plants) PersistenceUtils.partialUpdate(currentPlant, resource);
        plantService.save(resource);
        model.addAttribute("plants", plantService.findAll());
        return "plants";
    }

}
