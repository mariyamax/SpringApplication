package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Repositories.ImagesRepository;
import org.example.Services.PlantsService;
import org.example.Services.UsersService;
import org.example.Utils.CustomTokenUtils;
import org.example.Utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/view/plants")
public class PlantViewController {

    @Autowired
    private PlantsService plantService;
    @Autowired
    private UsersService usersService;

    @Autowired
    private ImagesRepository imagesRepository;

    @GetMapping("")
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

    @GetMapping("/main")
    public String plants(Model model) {
        model.addAttribute("plants", plantService.findAll());
        return "plants";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Plants resource, Model model, @RequestParam(name = "file", required = false) MultipartFile file, String token) {
        Users user = usersService.findByName(CustomTokenUtils.encodeToUsername(token));

        plantService.save(resource, file, user);
        model.addAttribute("plants", plantService.findAll());
        return "plants";
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public String sell(@RequestParam(name = "name", required = false) String name, Principal principal, Model model) {
        Plants plant = plantService.findByName(name);
        Users buyer = usersService.findByName(principal.getName());
        Users owner = usersService.findById(plant.getUserId());
        plantService.sellPlant(buyer, owner, plant);
        model.addAttribute("plants", plantService.findAll());
        return "redirect:/view/plants/main";
    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String update(Plants resource, @RequestParam(name = "file", required = false) MultipartFile file, Principal principal, Model model) {
        System.out.println("heyPut");
        return "redirect:/view/plants/main";
        //check not null
        /*Users users = usersService.findByName(principal.getName());
        if (!file.isEmpty()) {
            plantService.addImageToPlant(resource, file);
        }
        Plants currentPlant = plantService.findByName(resource.getName());
        resource = (Plants) PersistenceUtils.partialUpdate(currentPlant, resource);
        plantService.save(resource);
        model.addAttribute("plants", plantService.findAll());
        return "redirect:/view/plants/main";*/
    }

}
