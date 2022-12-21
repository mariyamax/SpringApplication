package org.example.Repositories;

import org.example.Models.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRepository extends JpaRepository<RefreshTokens, Long> {

    RefreshTokens findByValue(String value);

}
