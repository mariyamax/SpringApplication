package org.example.Services;

import lombok.RequiredArgsConstructor;
import org.example.Models.Images;
import org.example.Models.Plants;
import org.example.Models.Users;
import org.example.Repositories.PlantsRepository;
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
    private ImagesService imagesService;

    @Autowired
    private UsersService usersService;

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

    public Boolean sell(Users buyer, Users owner, Plants plant) {
        if (buyer.getCoins() >= plant.getCoast()) {
            buyer.setCoins(buyer.getCoins() - plant.getCoast());
            owner.setCoins(owner.getCoins() + plant.getCoast());
            usersService.update(buyer);
            usersService.update(owner);
            plant.setUserId(buyer.getSid());
            plantRepository.save(plant);
            return true;
        }
        else return false;
    }

    public void addImage(Plants resource, MultipartFile file) {
        Images image = new Images();
        image.toImageEntity(file);
        imagesService.save(image);
        resource.setImageId(image.getSid());
    }

    public Long create(Plants resource, MultipartFile file1, Users user) {
        resource.setUserId(user.getSid());
        resource.setAuthor(user.getUsername());
        if (!file1.isEmpty()) {
            addImage(resource, file1);
        }
        plantRepository.save(resource);
        usersService.update(user);
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
