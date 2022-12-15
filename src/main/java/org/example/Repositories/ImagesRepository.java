package org.example.Repositories;

import org.example.Models.Images;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImagesRepository extends JpaRepository<Images, Long> {
    Images findByID(Long id);

}

