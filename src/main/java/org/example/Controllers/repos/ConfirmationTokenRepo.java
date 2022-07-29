package org.example.Controllers.repos;

import org.example.Controllers.model.ConfirmationToken;
import org.example.Controllers.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken,String> {
        ConfirmationToken findByConfirmationToken(String confirmationToken);

}
