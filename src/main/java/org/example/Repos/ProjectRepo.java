package org.example.Repos;

import org.example.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project,Long> {

  Project findByID(Long id);

  Project findByName(String name);

}
