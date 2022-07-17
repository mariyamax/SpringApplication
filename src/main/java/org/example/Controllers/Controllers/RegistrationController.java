package org.example.Controllers.Controllers;

import org.example.Controllers.domain.Role;
import org.example.Controllers.domain.User;
import org.example.Controllers.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    public static User myUser;
    @Autowired
    private UserRepo userRepo;
@GetMapping("/registration")
    public String registration(){
        return "registration";
    }

@PostMapping("/registration")
    public String addUser(User user, Map<String,Object> model){
    User userFromDB = userRepo.findByUsername(user.getUsername());
    myUser = userFromDB;
    if(userFromDB!=null){
        model.put("message","User exists!");
        return "registration";
    }
    user.setActive(true);
    user.setRoles(Collections.singleton(Role.USER));
    userRepo.save(user);
    return "redirect:/login";
}
    @PostMapping("/login")
    public String login(){
    return "redirect:/main";
    }
}
