package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.Models.RefreshTokens;
import org.example.Models.Users;
import org.example.Services.CustomEmailService;
import org.example.Services.CustomTokenService;
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
    private CustomEmailService mailService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CustomTokenService tokenService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String save(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password,
                       @RequestParam(name = "mail") String mail, Model model) {
        usersService.create(login);
        RefreshTokens tokens = tokenService.encodeToRefreshToken(login,password,mail);
        String accessToken = tokenService.getAccessToken(tokens);
        mailService.sendSimpleEmail(mail,"Token Creation", "Hi from plants application! This is your token "+accessToken);
        return "registration";
    }

}
