package org.example.Repositories;

import org.example.Models.Plants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantsRepository extends JpaRepository<Plants, Long> {

    Plants findByName(String name);

    Plants findByID(Long id);

}
