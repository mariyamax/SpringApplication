package org.example.Repositories;

import org.example.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByToken(String token);

    Users findBySid(Long id);

}
