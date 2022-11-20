package org.example.Controllers;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.example.Models.Project;
import org.example.Models.User;
import org.example.Services.ProjectService;
import org.example.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ProjectController {

  private final ProjectService projectService;

  private final UserService userService;

  @PostMapping("/projects")
  public String profile(Principal principal, @RequestParam("name") String name,
      @RequestParam("area") String area, @RequestParam("description") String description) {
    User user = userService.getByUserMail(principal.getName());
    Project project = new Project();
    projectService.createProject(user,project,name,area,description);
    return "redirect:/homePage";
  }

}
