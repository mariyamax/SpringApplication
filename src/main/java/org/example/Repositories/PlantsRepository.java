package org.example.Repositories;

import org.example.Models.Plants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlantsRepository extends JpaRepository<Plants, Long> {

    Plants findByName(String name);

    Plants findBySid(Long id);

}
