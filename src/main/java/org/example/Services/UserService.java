package org.example.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Enums.Role;
import org.example.Enums.University;
import org.example.Models.User;
import org.example.Repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
private final UserRepo userRepository;

private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        user.getUniversities().add(University.NONE);
        userRepository.save(user);
        return true;
    }

    public void saveUser(User user){
        userRepository.save(user);}

    public List<User> getAllUsers(){
        return userRepository.findAll();}

    public User getByUserMail(String email){
    return userRepository.findByEmail(email);}



    public void ChangeRole(String email){
    User user = userRepository.findByEmail(email);
    user.getRoles().clear();
    user.getRoles().add(Role.ROLE_ADMIN);
    userRepository.save(user);
    }


    public User findByID(Long aLong) {
        return userRepository.findByID(aLong);
    }
}
