package com.juansecu.opentoonix.avatars.models.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.juansecu.opentoonix.avatars.enums.EAvatarBodyPartType;

@Data
@Entity(name = "Avatar_body_parts")
public class AvatarBodyPartEntity {
    @Column(name = "Avatar_body_part_id")
    @Id
    private Integer avatarBodyPartId;
    @Column(length = 45, name = "Title", nullable = false)
    private String title;
    @Column(name = "Category_id", nullable = false)
    private int categoryId;
    @Column(name = "Type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EAvatarBodyPartType type;
    @Column(name = "Price", nullable = false)
    private int price;
    @Column(length = 100, name = "Path", nullable = false)
    private String path;
    @Column(name = "Added_at", updatable = false)
    @CreationTimestamp
    private Date addedAt;
    @Column(name = "Updated_at")
    @UpdateTimestamp
    private Date updatedAt;
    @JoinColumn(
        insertable = false,
        name = "Category_id",
        referencedColumnName = "Avatar_body_part_category_id",
        updatable = false
    )
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private AvatarBodyPartCategoryEntity category;
}
