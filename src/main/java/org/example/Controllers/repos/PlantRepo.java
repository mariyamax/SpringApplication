package org.example.Controllers.repos;


import org.example.Controllers.model.Plant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepo extends JpaRepository<Plant,Long> {

    Plant findByName(String username);
}
