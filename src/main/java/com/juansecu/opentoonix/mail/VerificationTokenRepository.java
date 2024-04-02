package com.juansecu.opentoonix.mail;

/* --- Third-party modules --- */
import org.springframework.data.jpa.repository.JpaRepository;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.models.entities.UserEntity;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(UserEntity user);
}
