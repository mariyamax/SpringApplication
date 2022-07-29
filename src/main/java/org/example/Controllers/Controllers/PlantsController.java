package org.example.Controllers.Controllers;


import org.example.Controllers.Enums.Role;
import org.example.Controllers.Services.PlantService;
import org.example.Controllers.model.Plant;
import org.example.Controllers.model.User;
import org.example.Controllers.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class PlantsController {
    @Autowired
    private PlantService plantService;

    @Autowired
    private UserRepo userRepo;



    @GetMapping("/")
    public String plants(Model model, Principal principal){
        User user = plantService.getUserByPrincipal(principal);
        if(user.getRoles().contains(Role.ROLE_ADMIN)){
            model.addAttribute("admin","adminpanel");
        }
        model.addAttribute("plants",plantService.listPlant());
        model.addAttribute("user",user);
        model.addAttribute("email",user.getEmail());
        model.addAttribute("uName",user.getUname());
        return "plant";
    }
    @GetMapping("/market")
    public String marketPage(Model model,Principal principal){
        List<Plant> plants = new ArrayList<>();
        List<User> users = new ArrayList<>();
        for (Plant plant: plantService.listPlant()
             ) {
            System.out.println(plant.getName());
            System.out.println(plant.getIsSale());
            if(plant.getIsSale()){
                plants.add(plant);
                users.add(plant.getUser());
            }
        }

        model.addAttribute("plants",plants);
        model.addAttribute("users",users);
        model.addAttribute("plant",plants.iterator().next());
        model.addAttribute("user",users.iterator().next());
        return "market";
    }
    @PostMapping("/plant/buy/{name}")
    public String byPlant(@PathVariable String name, Principal principal){
        System.out.println(name);
        System.out.println("ура ура вы все купили");
        Plant plant = plantService.getPlantByName(name);
        User Buyer = plantService.getUserByPrincipal(principal);
        User Owner = plant.getUser();
        if(plant.getCoast()>Buyer.getCoins()){
            return "market";
        }
        Buyer.setCoins(Buyer.getCoins()-plant.getCoast());
        userRepo.save(Buyer);
        Owner.setCoins(Owner.getCoins()+plant.getCoast());
        userRepo.save(Owner);
        plant.setUser(Buyer);
        plant.setAuthor(Buyer.getUname());
        plantService.simpleSave(plant);
        System.out.println(plant.getUser().getEmail());
        return "redirect:/market";
    }
    @GetMapping("/plant/{name}")
    public String plantInfo(@PathVariable String name, Model model){
        Plant plant = plantService.getPlantByName(name);
        User user = plant.getUser();
        model.addAttribute("plant",plant);
        model.addAttribute("user",user);
        return "plant-info";
    }
    @PostMapping("/plant/info")
    public String plantSearch(@RequestParam("name") String name, Model model){
        Plant plant = plantService.getPlantByName(name);
        User user = plant.getUser();
        model.addAttribute("plant",plant);
        model.addAttribute("user",user);
        return "plant-info";
    }
    @PostMapping("/plant/create")
    public String createPlant(Principal principal, @RequestParam("file1") MultipartFile file1,
                              @RequestParam("name") String name, @RequestParam("type") String type,
                              @RequestParam("area") String area, @RequestParam("description") String description,
                              @RequestParam("coast") String coast) throws IOException {
        Plant plant = new Plant();
        plantService.savePlant(principal,plant,file1,name,type, area, description,coast);
        return "redirect:/";
    }
    @GetMapping("/create")
    public String CreatePage(){
        return "createPlant";
    }

    @PostMapping("/plant/delete/{name}")
    public String deletePlant(@PathVariable String name){
        plantService.deletePlant(name);
        return "redirect:/";
    }
    @PostMapping("sell/{name}")
    public String sell(@PathVariable String name){
        plantService.putUpForSale(name);
        return "redirect:/market";
    }
}