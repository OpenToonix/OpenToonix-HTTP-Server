package com.juansecu.opentoonix.avatars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juansecu.opentoonix.avatars.models.entities.AvatarEntity;

@Repository
public interface IAvatarsRepository extends JpaRepository<AvatarEntity, Integer> {
}
