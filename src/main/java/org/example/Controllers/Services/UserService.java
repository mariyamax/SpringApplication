package org.example.Controllers.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Controllers.Enums.Role;
import org.example.Controllers.model.Image;
import org.example.Controllers.model.User;
import org.example.Controllers.repos.ImageRepository;
import org.example.Controllers.repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
private final UserRepo userRepository;
private final ImageRepository imageRepository;
private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        user.setCoins(10);
        user.setRegular(1);
        user.setLastVisitTime(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    public void saveUser(User user){
        userRepository.save(user);}

    public List<User> getAllUsers(){
        return userRepository.findAll();}

    public User getByUserMail(String email){
    return userRepository.findByEmail(email);}

    public void banUser(String email) {
      User user = userRepository.findByEmail(email);
      if(user!=null){
          user.setActive(!user.isActive());
      }
      userRepository.save(user);
    }

    public void ChangeRole(String email){
    User user = userRepository.findByEmail(email);
    user.getRoles().clear();
    user.getRoles().add(Role.ROLE_ADMIN);
    userRepository.save(user);
    }

    public void addAvatar(MultipartFile avatar, Principal principal)  throws IOException {
        Image image = new Image();
        image.toImageEntity(avatar,image);
        imageRepository.save(image);
        User user = userRepository.findByEmail(principal.getName());
        user.setAvatar(image);
        userRepository.save(user);
    }

    public User findByID(Long aLong) {
        return userRepository.findByID(aLong);
    }
}
