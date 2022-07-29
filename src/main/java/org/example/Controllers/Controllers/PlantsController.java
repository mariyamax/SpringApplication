package org.example.Controllers.Controllers;

import org.example.Controllers.domain.Message;
import org.example.Controllers.repos.MessageRepo;
import org.example.Controllers.repos.PlantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Autowired
private PlantRepo plantRepo;



@GetMapping("/plant")
public String plants(@RequestParam(name="name", required = false) String name, Model model){
        model.addAttribute("plants",plantRepo.findAll());
        return "plants";
        }
@GetMapping("/plant/{name}")
public String plantInfo(@PathVariable String name, Model model){
        Plant plant = plantRepo.findByName(name).get(0);
        model.addAttribute("name",plant.getName());
        model.addAttribute("area",plant.getArea());
        model.addAttribute("type",plant.getType());
        model.addAttribute("description",plant.getDescription());
        return "plant-info";
        }
@PostMapping("/plant/create")
public String createPlant(Plant plant){
        plantRepo.save(plant);
        return "redirect:/plant";
        }
@PostMapping("/plant/delete/{name}")
public String deletePlant(@PathVariable String name){
        plantRepo.delete(plantRepo.findByName(name).get(0));
        return "redirect:/plant";
        }
}