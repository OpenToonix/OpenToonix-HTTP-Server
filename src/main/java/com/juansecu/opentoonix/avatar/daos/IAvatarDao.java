package com.juansecu.opentoonix.avatar.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juansecu.opentoonix.avatar.models.entities.AvatarEntity;

public interface IAvatarDao extends JpaRepository<AvatarEntity, String> {
}
