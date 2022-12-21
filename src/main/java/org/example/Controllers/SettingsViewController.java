package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.Models.Plants;
import org.example.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/settings")
public class SettingsViewController {

    @GetMapping
    public String settingsForm(Model model) {
        model.addAttribute("plant", new Plants());
        return "settings";
    }

}
