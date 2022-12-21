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
        Users users = usersService.create(login);
        RefreshTokens tokens = tokenService.encodeToRefreshToken(login,password,mail);
        String accessToken = tokenService.getAccessToken(tokens);
        model.addAttribute("token", accessToken);
        System.out.println(accessToken);
        return "registration";
    }

}
