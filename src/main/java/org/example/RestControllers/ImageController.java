package org.example.RestControllers;

import org.example.Models.Images;
import org.example.Repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
public class ImageController {

    @Autowired
    private ImagesRepository imageRepository;

    @GetMapping("api/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Images image = imageRepository.findBySid(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getFileExtension()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

}
