package org.example.Services;

import lombok.RequiredArgsConstructor;
import org.example.Models.Images;
import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Repositories.ImagesRepository;
import org.example.Repositories.PlantsRepository;
import org.example.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantsService {

    @Autowired
    private PlantsRepository plantRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private UsersRepository usersRepository;

    public List<Plants> findAll() {
        return plantRepository.findAll();
    }

    public Plants findById(Long id) {
        return plantRepository.findBySid(id);
    }

    public Long save(Plants resource) {
        Plants plant = plantRepository.save(resource);
        return plant.getSid();
    }

    public Boolean sellPlant(Users buyer, Users owner, Plants plant) {
        if (buyer.getCoins() >= plant.getCoast()) {
            buyer.setCoins(buyer.getCoins() - plant.getCoast());
            owner.setCoins(owner.getCoins() +
                    plant.getCoast());
            usersRepository.save(buyer);
            usersRepository.save(owner);
            plant.setUserId(buyer.getSid());
            plant.setAuthor(buyer.getUsername());
            plantRepository.save(plant);
            return true;
        }
        else return false;
    }

    public void addImageToPlant(Plants resource, MultipartFile file) {
        Images image = new Images();
        image.toImageEntity(file);
        imagesRepository.save(image);
        resource.setImageId(image.getSid());
    }

    public Long save(Plants resource, MultipartFile file1, Users user) {
        resource.setUserId(user.getSid());
        resource.setAuthor(user.getUsername());
        addImageToPlant(resource, file1);
        plantRepository.save(resource);
        user.getPlants().add(resource);
        usersRepository.save(user);
        return resource.getSid();
    }

    public void deleteById(Long id) {
        Plants plantToDelete = plantRepository.findBySid(id);
        plantRepository.delete(plantToDelete);
    }

    public Plants findByName(String name) {
        return plantRepository.findByName(name);
    }

}
