package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.Models.Plants;
import org.example.Repositories.ImagesRepository;
import org.example.Services.PlantsService;
import org.example.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PlantViewController {

    @Autowired
    private PlantsService plantService;
    @Autowired
    private UsersService usersService;

    @Autowired
    private ImagesRepository imagesRepository;

    @GetMapping("/plant")
    public String plantSearching(@RequestParam String name, Model model) {
        Plants plant = new Plants();
        try {
            plant = plantService.findByName(name);
        } catch (NullPointerException e) {
            return "plants";
        }
        model.addAttribute("plant", plant);
        model.addAttribute("user", usersService.findById(plant.getUserId()));
        return "plantProfile";
    }

    @GetMapping("/plants")
    public String plants(Model model) {
        model.addAttribute("plants", plantService.findAll());
        return "plants";
    }

}
