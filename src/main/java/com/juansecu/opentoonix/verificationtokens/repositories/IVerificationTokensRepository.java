package com.juansecu.opentoonix.verificationtokens.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.juansecu.opentoonix.users.models.entities.UserEntity;
import com.juansecu.opentoonix.verificationtokens.enums.EVerificationTokenType;
import com.juansecu.opentoonix.verificationtokens.models.entities.VerificationTokenEntity;

@Repository
public interface IVerificationTokensRepository extends CrudRepository<VerificationTokenEntity, Integer> {
    Optional<VerificationTokenEntity> findByTokenAndTypeAndUser(
        UUID decryptedToken,
        EVerificationTokenType type,
        UserEntity user
    );
}
