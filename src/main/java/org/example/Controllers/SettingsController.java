package org.example.Controllers;

import java.security.Principal;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.Models.Project;
import org.example.Models.User;
import org.example.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class SettingsController {

  private final UserService userService;

  @GetMapping("/settings/{id}")
  public String settings(Principal principal, Model model){
    User user = userService.getByUserMail(principal.getName());
    //Set<Project> projects = user.getProjects();
    model.addAttribute("user",user);
    //model.addAttribute("projects",projects);
    return "settings";
  }

  /*@PostMapping("/progile/{id}")
  public String profile(){

  }*/

}
