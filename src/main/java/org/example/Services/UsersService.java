package org.example.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Models.Users;
import org.example.Repositories.ImagesRepository;
import org.example.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {


    @Autowired
    private final UsersRepository userRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    public Users create(String login) {
        Users user = new Users();
        user.setCoins(10);
        user.setUsername(login);
        if (userRepository.findByUsername(login)==null) {
           return userRepository.save(user);
        } else {
            return null;
        }
    }

    public void update(Users user) {
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        Users userToDelete = userRepository.findBySid(id);
        userRepository.delete(userToDelete);
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users findById(Long id) {
        return userRepository.findBySid(id);
    }
}
