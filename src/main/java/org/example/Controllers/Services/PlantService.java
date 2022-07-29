package org.example.Controllers.Services;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Controllers.model.Image;
import org.example.Controllers.model.Plant;
import org.example.Controllers.model.PlantBuilder;
import org.example.Controllers.model.User;
import org.example.Controllers.repos.PlantRepo;
import org.example.Controllers.repos.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepo plantRepo;
    private final UserRepo userRepo;
    public List<Plant> listPlant() {
        return plantRepo.findAll();
    }

    public void simpleSave(Plant plant){
        plantRepo.save(plant);
    }

    public void savePlant(Principal principal, Plant plant, MultipartFile file1, String name, String type, String area, String description, String coast) throws IOException {
        PlantBuilder plantBuilder = new PlantBuilder();
        plant = plantBuilder.area(area).PlantName(name).
                PlantType(type).PlantUser(getUserByPrincipal(principal))
                .description(description).coast(Integer.parseInt(coast)).
                author(getUserByPrincipal(principal).getUname()).isSale(false).build();
        Image image = new Image();
        image.toImageEntity(file1,image);
        plant.addImageToPlant(image);
        Plant plantFromDb = plantRepo.save(plant);
        plantFromDb.setPreviewImageId(plantFromDb.getImages().get(0).getID());
        plantRepo.save(plant);
    }

    public User getUserByPrincipal(Principal principal) {
        if(principal==null){
            return new User();
        }
        return userRepo.findByEmail(principal.getName());
    }


    public void deletePlant(String name) {
        plantRepo.delete(plantRepo.findByName(name));
    }

    public Plant getPlantByName(String name) {
        return plantRepo.findByName(name);
    }

    public void putUpForSale(String name) {
        Plant plant = plantRepo.findByName(name);
        plant.setIsSale(true);
        plantRepo.save(plant);
    }
}
