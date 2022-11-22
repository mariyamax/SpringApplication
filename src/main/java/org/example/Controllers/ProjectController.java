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
    return "projectsPages";
  }

  @PostMapping("/projects/{name}")
  private String updateProject(@PathVariable("name") String projectName, String userId){
    Project project = projectService.getProject(projectName);
    Long id = Long.parseLong(userId);
    project.getUsers().add(userService.findByID(Long.parseLong(userId)));
    projectService.saveProject(project);
    return "redirect:/homePage";
  }

 @GetMapping("/projects")
  private String projectsList(Principal principal, Model model){
    User user = userService.getByUserMail(principal.getName());
    List<Project> projects = projectService.searchByUserId(user.getID());
    if (user.isAdmin()){
    projects.addAll(projectService.searchByAdminId(user.getID()));}
    model.addAttribute("user",user);
    model.addAttribute("projects", projects);
    return "projectsPage";
  }

/*  @GetMapping("/project/{name}")
  private String projectsByName(@PathVariable String name){
    //todo фильтрация на выборку проектов по имени
  }*/
}
