package com.juansecu.opentoonix.avatars.models.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity(name = "Avatar_costumes")
public class AvatarCostumeEntity {
    @Column(name = "Avatar_costume_id")
    @Id
    private Integer avatarCostumeId;
    @Column(length = 45, name = "Title", nullable = false, unique = true)
    private String title;
    @Column(name = "Category_id", nullable = false)
    private int categoryId;
    @Column(name = "Price", nullable = false)
    private int price;
    @Column(length = 100, name = "Head_path", nullable = false)
    private String headPath;
    @Column(length = 100, name = "Body_path", nullable = false)
    private String bodyPath;
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
