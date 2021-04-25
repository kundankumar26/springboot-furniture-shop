package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.ConfirmationToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    @Query(value = "SELECT * FROM confirmation_token_table c WHERE c.confirmation_token LIKE :confirmationToken", nativeQuery = true)
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
