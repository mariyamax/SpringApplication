package org.example.Services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Enums.Area;
import org.example.Models.Project;
import org.example.Models.User;
import org.example.Repos.ProjectRepo;
import org.example.Repos.UserRepo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepo projectRepository;
  private final UserRepo userRepo;

  public boolean createProject(User user, Project project, String name, String area, String description) {
    if (projectRepository.findByName(name) != null) return false;
    project.setName(name);
    project.setDescription(description);
    project.getAreas().add(getArea(area));
    project.getUsers().add(user);
    project.setAdminId(user.getID());
    projectRepository.save(project);
    return true;
  }
  //todo add utils like area-factory
  private Area getArea(String area){
    switch (area) {
      case ("MUSIC"): return Area.MUSIC;
      case ("SCIENCE"): return Area.SCIENCE;
      case ("DEVELOPMENT"): return Area.DEVELOPMENT;
      default: return null;
    }
  }

  public Project getProject(Long id) {
    return projectRepository.findByID(id);
  }

  public void saveProject(Project project) {
    projectRepository.save(project);
  }

  public Project getProject(String projectName) {
    return projectRepository.findByName(projectName);
  }

  public List<Project> searchByName(String name){
    return projectRepository.findAll().stream().filter(project -> project.getName().contains(name)).toList();
  }

  public List<Project> searchByUserId(User user) {
    System.out.println("hi from searching by user");
    return projectRepository.findAll().stream().filter(project -> project.getUsers().contains(user)).toList();
  }

  public List<Project> searchByAdminId(Long id) {
    return projectRepository.findAll().stream().filter(project -> project.getAdminId()==id).toList();
  }

}
