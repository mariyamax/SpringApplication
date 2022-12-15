package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/settings")
public class SettingsViewController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public String settingsForm(Principal principal, Model model) {
        Users user = usersService.findByName(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("plant", new Plants());
        model.addAttribute("property", user.getPlants());
        return "settings";
    }

}
