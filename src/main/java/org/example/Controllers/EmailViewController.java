package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.Models.Users;
import org.example.Services.CustomEmailService;
import org.example.Services.UsersService;
import org.example.Services.CustomTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*@Controller
@AllArgsConstructor
/*public class EmailViewController {

    @Autowired
    private CustomEmailService mailService;
    //todo check port
    //todo remove mail from user data
    @Autowired
    private UsersService usersService;

    @GetMapping("/recovery")
    public String registration() {
        return "recoveryPage";
    }

    @PostMapping("/recovery")
    public String save(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password,
                       @RequestParam(name = "password") String mail, Model model) {
        String token = CustomTokenService.encodeToToken(login,password,mail);
        Users user = usersService.findByUsername(CustomTokenService.decodeToUsername(token));
        if (user == null) {
            model.addAttribute("error","Can not find user by credentials");
            return "recovery";
        }
        mailService.sendSimpleEmail(mail,"Token Recovery", "Hi from plants application! This is your token "+user.getToken());
        model.addAttribute("error","Can not find user by credentials");
        return "recovery";
    }
}*/
