package org.example.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Models.Images;
import org.example.Models.Users;
import org.example.Repositories.ImagesRepository;
import org.example.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UsersRepository userRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Users findById(Long id) {
        return userRepository.findByID(id);
    }

    public Long save(Users resource) {
        resource.setPassword(passwordEncoder.encode(resource.getPassword()));
        Users user = userRepository.save(resource);
        return user.getID();
    }

    public void update(Users user) {
        userRepository.save(user);
    }

    public void addImageToUser(Users resource, MultipartFile file) {
        Images image = new Images();
        image.toImageEntity(file);
        imagesRepository.save(image);
        resource.setImageId(image.getID());
    }

    public void updateCoins(Users user) {
        int now = LocalDateTime.now().getDayOfMonth();
        int lastVisit = user.getLastVisitTime().getDayOfMonth();
        if (now - lastVisit >= 1) {
            if (now - lastVisit == 1) {
                if (user.getRegular() < 7) {
                    user.setRegular(user.getRegular() + 1);
                    user.setCoins(user.getCoins() + user.getRegular());
                } else {
                    user.setCoins(user.getCoins() + user.getRegular());
                }
            } else {
                user.setRegular(1);
                user.setCoins(user.getCoins() + 1);
            }
            userRepository.save(user);
        }
    }

    public void deleteById(Long id) {
        Users userToDelete = userRepository.findByID(id);
        userRepository.delete(userToDelete);
    }

    public Users findByName(String name) {
        return userRepository.findByUsername(name);
    }

}
