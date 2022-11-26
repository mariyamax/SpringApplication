package org.example.Controllers;

import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.Models.Project;
import org.example.Models.User;
import org.example.Services.ProjectService;
import org.example.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Controller
public class UserController {

  private final ProjectService projectService;

  private final UserService userService;

  @GetMapping("/users")
  public String homePage(Principal principal, Model model) {
    User user = userService.getByUserMail(principal.getName());
    model.addAttribute("user",user);
    return "homePage";
  }

  @GetMapping("/users/{name}")
  public String userPage(@PathVariable String name, Model model,Principal principal) {
    User user = userService.getByUserMail(principal.getName());
    User pageOwner = userService.getByName(name);
    if(user.getID()== pageOwner.getID()) {
      model.addAttribute("isOwner",true);
    } else {
      model.addAttribute("isOwner",false);
    }
    List<Project> usersProject = projectService.searchByUserId(pageOwner);
    model.addAttribute("projects",usersProject);
    model.addAttribute("user",user);
    return "userPage";
  }

  @PostMapping("/users/{name}")
  public String updateUser(@PathVariable String name) {
    //todo updating
    return "userPage";
  }
}
