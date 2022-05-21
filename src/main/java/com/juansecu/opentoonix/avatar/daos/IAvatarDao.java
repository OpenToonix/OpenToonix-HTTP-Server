package com.juansecu.opentoonix.avatar.daos;

/* --- Third-party modules --- */
import org.springframework.data.jpa.repository.JpaRepository;

/* --- Application modules --- */
import com.juansecu.opentoonix.avatar.models.entities.AvatarEntity;

/**
 * Represents database operations related to avatars.
 */
public interface IAvatarDao extends JpaRepository<AvatarEntity, Integer> {
}
