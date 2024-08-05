package com.juansecu.opentoonix.avatars.models.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity(name = "Avatar_body_part_categories")
public class AvatarBodyPartCategoryEntity {
    @Column(name = "Avatar_body_part_category_id")
    @Id
    private Integer avatarBodyPartCategoryId;
    @Column(length = 45, name = "Name", nullable = false, unique = true)
    private String name;
    @Column(name = "Added_at", updatable = false)
    @CreationTimestamp
    private Date addedAt;
    @Column(name = "Updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
