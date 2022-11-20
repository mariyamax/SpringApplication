package org.example.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Enums.Area;
import org.example.Enums.Role;
import org.example.Enums.University;
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
    project.getUser().add(user);
    project.getAreas().add(getArea(area));
    projectRepository.save(project);
    user.getProjects().add(projectRepository.findByName(name));
    userRepo.save(user);
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
}
