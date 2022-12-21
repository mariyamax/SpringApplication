package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.Models.Users;
import org.example.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class SecurityViewController {

    @Autowired
    private UsersService service;


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String save(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password, Model model) {
        Users users = service.create(login, password);
        if (users != null) {
            model.addAttribute("token", users.getToken());
        }
        return "registration";
    }

}
