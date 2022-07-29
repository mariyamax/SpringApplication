package org.example.Controllers.repos;

import org.example.Controllers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByEmail(String email);

    User findByID(Long id);
}
