package org.example.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Models.Users;
import org.example.Repositories.ImagesRepository;
import org.example.Repositories.UsersRepository;
import org.example.Utils.CustomTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {


    @Autowired
    private final UsersRepository userRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    public Users create(String login, String password) {
        Users user = new Users();
        user.setCoins(10);
        user.setToken(CustomTokenUtils.encodeToToken(login, password));
        if (userRepository.findByToken(user.getToken())!=null) {
            return null;
        } else {
            return userRepository.save(user);
        }
    }

    public void update(Users user) {
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        Users userToDelete = userRepository.findBySid(id);
        userRepository.delete(userToDelete);
    }

    public Users findByToken(String token) {
        return userRepository.findByToken(token);
    }

}
