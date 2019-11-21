package com.taras.user.demo.repository;

import com.taras.user.demo.domain.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmactionTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
