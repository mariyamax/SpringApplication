package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.Models.Users;
import org.example.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserViewController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/{id}")
    public String userInfo(@PathVariable("id") Long id, Model model) {
        Users user = usersService.findById(id);
        System.out.println("hi");
        model.addAttribute("user", user);
        model.addAttribute("property", user.getPlants());
        return "userProfile";
    }

    @GetMapping()
    public String userProfile(Principal principal, Model model) {
        Users user = usersService.findByName(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("property", user.getPlants());
        return "userProfile";
    }

}
