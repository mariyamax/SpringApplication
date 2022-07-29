package org.example.Controllers.repos;

import org.example.Controllers.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findByID(Long id);
}
