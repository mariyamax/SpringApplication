package org.example.Controllers.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.Controllers.Services.EmailSenderService;
import org.example.Controllers.Services.UserService;
import org.example.Controllers.model.ConfirmationToken;
import org.example.Controllers.model.User;
import org.example.Controllers.repos.ConfirmationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Controller
public class UserController {
    @Autowired
    private ConfirmationTokenRepo confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    private final UserService userService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String mainPage(){
        return "redirect:/";
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user) {
        User existingUser = userService.getByUserMail(user.getEmail());
        if(existingUser != null)
        {
            return "redirect:/registration";
        }
        else
        {   userService.createUser(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("set mail here");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8078/confirm?token="+confirmationToken.getConfirmationToken());
            emailSenderService.sendEmail(mailMessage);
        }

        return "redirect:/login";
    }


    @GetMapping("/confirm")
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null) {
            User user = userService.getByUserMail(token.getUser().getEmail());
            user.setActive(true);
            userService.saveUser(user);
            return "plant";
        }
        return "redirect:/login";
    }

    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable("id") String id,Model model,Principal principal){
        User user = userService.findByID(Long.decode(id));
        if(user.getAvatar()!=null){
            model.addAttribute("avatar","avatar exist");
            model.addAttribute("id",user.getAvatar().getID());
        }
        if(principal.getName().equals(user.getEmail())){
            model.addAttribute("homepage","mypage");
        }
        model.addAttribute("owner",userService.getByUserMail(principal.getName()));
        model.addAttribute("coins",user.getCoins());
        model.addAttribute("user",user);
        model.addAttribute("uName",user.getUname());
        model.addAttribute("plants",user.getPlants());
        return "user-info";
    }
    @PostMapping("/foto")
    public String addAvatar(@RequestParam("avatar") MultipartFile avatar, Principal principal) throws IOException {
        userService.addAvatar(avatar, principal);
        return "redirect:/";
    }

    @PostMapping("/coins")
    public String userCoins(Principal principal){
       User user = userService.getByUserMail(principal.getName());
       System.out.println((LocalDateTime.now().getDayOfMonth()-user.getLastVisitTime().getDayOfMonth()));
       if(((LocalDateTime.now().getDayOfMonth()-user.getLastVisitTime().getDayOfMonth())>=1)){
           user.setCoins(user.getCoins()+user.getRegular());
           user.setLastVisitTime(LocalDateTime.now());
           userService.saveUser(user);
           if(user.getRegular()<7){
               user.setRegular(user.getRegular()+1);
               userService.saveUser(user);
           }
       }
       return "redirect:/";
    }
}
