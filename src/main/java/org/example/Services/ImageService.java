package org.example.Services;

import org.example.Models.Images;
import org.example.Repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImagesRepository imagesRepository;

    public Images findBySid(Long id) {
        return imagesRepository.findBySid(id);
    }
}
