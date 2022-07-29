package org.example.Controllers.repos;


import org.example.Controllers.domain.Plant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepo extends JpaRepository<Plant,Long> {

    Plant findByUsername(String username);
}
